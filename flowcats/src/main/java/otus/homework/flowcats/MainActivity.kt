package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsViewModel.state
            .onEach { state ->
                state.fact?.let { fact -> view.populate(fact) }
            }
            .launchIn(lifecycleScope)

        catsViewModel.events
            .onEach { event ->
                when (event) {
                    is CatsEvent.Error -> showError(event.cause)
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}