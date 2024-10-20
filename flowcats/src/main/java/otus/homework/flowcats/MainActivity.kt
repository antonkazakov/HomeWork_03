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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsStateFlow.collect { state ->
                    when (state) {
                        is Result.Error -> Toast.makeText(
                            this@MainActivity,
                            state.e.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        is Result.Loading -> Toast.makeText(
                            this@MainActivity,
                            R.string.loading,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        is Result.Success -> view.populate(state.value)
                    }
                }
            }
        }
    }
}