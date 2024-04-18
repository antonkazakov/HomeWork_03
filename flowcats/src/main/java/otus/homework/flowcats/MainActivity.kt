package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsState.collectLatest { result ->
                when (result) {
                    Result.Error -> {
                        view.showError()
                    }

                    is Result.Success<*> -> {
                        (result.value as? Fact)?.let {
                            view.populate(it)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}