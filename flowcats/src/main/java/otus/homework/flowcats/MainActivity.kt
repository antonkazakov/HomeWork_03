package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
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

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsFlow.collect { result ->
                    when (result) {
                        is Result.Success -> view.populate(result.data)
                        is Result.Error -> showToast(result.throwable)
                    }

                }
            }
        }
    }

    private fun showToast(throwable: Throwable) {
        val errorMessage =
            throwable.message ?: throwable.localizedMessage ?: getString(R.string.unknown_error)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}