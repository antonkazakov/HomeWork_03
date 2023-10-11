package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        lifecycleScope.launchWhenResumed {
            catsViewModel.catsFlow.collect { fact ->
                fact?.let { view.populate(it) }
            }
        }

        lifecycleScope.launchWhenResumed {
            catsViewModel.error.collect { error ->
                error?.let { view.errorMessage("Error: ${it.message}") }
            }
        }
    }
}