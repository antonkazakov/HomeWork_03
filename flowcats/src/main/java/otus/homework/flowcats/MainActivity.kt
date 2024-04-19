package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsStateFlow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data != null) {
                            view.populate(result.data)
                        } else {
                            view.showEmptyState()
                        }
                    }
                    is Result.Error -> {
                        view.showError(result.message)
                    }
                }
            }
        }
    }
}