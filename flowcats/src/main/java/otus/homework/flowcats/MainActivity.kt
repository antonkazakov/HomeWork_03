package otus.homework.flowcats

import MyResult
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
                catsViewModel.catFact.collect { result ->
                    when (result) {
                        is MyResult.Success -> view.populate(result.data)
                        is MyResult.Error -> {
                            val errorMessage = when {
                                result.errorResId != null -> this@MainActivity.getText(result.errorResId)
                                result.errorMsg != null -> result.errorMsg
                                else -> this@MainActivity.getText(R.string.app_unknown_error)
                            }
                            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                                .show()
                        }

                    }

                }
            }
        }
    }
}