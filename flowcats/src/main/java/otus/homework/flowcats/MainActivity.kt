package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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

        catsViewModel.factFlow
            .onEach {
                when (it) {
                    Result.Loading -> {}
                    is Result.Success -> view.populate(it.data)
                    is Result.Error -> showToast(it.message)
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            this,
            message ?: getString(R.string.unknown_error),
            Toast.LENGTH_SHORT
        ).show()
    }
}