package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenCreated {
            catsViewModel.catsStateFlow.collect {
                when (it) {
                    is Success -> view.populate(it.fact)
                    is Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            it.ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        view.populate(createEmptyFact().copy(text = "An error occurred."))
                    }
                }
            }
        }
    }
}