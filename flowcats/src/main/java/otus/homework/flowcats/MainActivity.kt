package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsStateFlow.collect { state ->
                when (state) {
                    is Result.Success -> view.populate(
                        (state.data as? Fact)?.text ?: getString(R.string.empty_cat_fact)
                    )
                    is Result.Loading -> view.showLoading()
                    is Result.Error -> view.showError(
                        state.error.message ?: getString(R.string.unknown_error_message)
                    )
                }
            }
        }
    }
}