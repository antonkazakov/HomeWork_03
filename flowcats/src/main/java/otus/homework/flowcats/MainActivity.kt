package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        lifecycleScope.launch {
            catsViewModel.catsStateData.collect { state ->
                when(state) {
                    is Result.Loading -> progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        view.populate(state.data)
                        progressBar.visibility = View.GONE
                    }
                    is Result.Error -> {
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }

    }
}
