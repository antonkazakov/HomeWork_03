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

                catsViewModel.state.collect { result ->
                    when(result){
                        is Success -> view.populate(result.data)

                        is Error -> Toast.makeText(
                            this@MainActivity,
                            "${result.errorCode}: ${result.errorMessage}",
                            Toast.LENGTH_SHORT
                        ).show()

                        is ResultException -> println(result.throwable.message)

                    }

                }
            }
        }
    }
}
