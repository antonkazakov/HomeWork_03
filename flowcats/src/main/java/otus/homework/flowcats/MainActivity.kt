package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.data.FactUiState

const val startTime: Long = 2000L
class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModel.CatsViewModelFactory(diContainer.repository)
    }
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.uiState.collect { state ->
                    when (state) {
                        is FactUiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            delay(startTime)
                        }
                        is FactUiState.Success -> {
                            view.populate(state.fact)
                            progressBar.visibility = View.GONE
                        }
                        is FactUiState.Error -> {
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
