package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.Result.Error
import otus.homework.flowcats.Result.Success

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsStateFlow.collect { result : Result<Fact>? ->
                    result ?: return@collect

                    when (result) {
                        is Success -> view.populate(result.data)
                        is Error -> Toast
                            .makeText(
                                this@MainActivity.baseContext,
                                result.message,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            }
        }
    }
}