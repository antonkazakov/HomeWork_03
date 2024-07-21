package otus.homework.flowcats

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val tag: String = javaClass.simpleName
    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        observeCatsFlow(view)
    }

    private fun observeCatsFlow(view: CatsView) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsStateFlow.collect {
                    when (it) {
                        is Result.Success -> view.populate(it.data)
                        is Result.Error -> showError(it.error)
                        else -> Log.e(tag, ERROR_UNKNOWN_STATE)
                    }
                }
            }
        }
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ERROR_UNKNOWN_STATE = "Unknown state"
    }
}