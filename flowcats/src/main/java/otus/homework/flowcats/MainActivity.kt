package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        performWithLifecycle {
            catsViewModel.catsStateFlow.collect { result ->
                val fact = resultOrNull<Fact>(result)
                fact?.let { view.populate(it) }
            }
        }
    }

    private inline fun <reified T> resultOrNull(result: Result): T? = when (result) {
        is Result.Success<*> -> result.result as T
        is Result.Error -> {
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun LifecycleOwner.performWithLifecycle(
        state: Lifecycle.State = STARTED,
        block: suspend () -> Unit
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state) {
                block()
            }
        }
    }
}