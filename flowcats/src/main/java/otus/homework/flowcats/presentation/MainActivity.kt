package otus.homework.flowcats.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import otus.homework.flowcats.DiContainer
import otus.homework.flowcats.R
import otus.homework.flowcats.models.domain.Result

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsViewModel.catsLiveData.observe(this) {
            view.populate(it.text)
        }

        lifecycleScope.launchWhenStarted {
            catsViewModel.uiState
                .collect {
                    when (it) {
                        is Result.Error -> {
                            view.populate(it.exception.message.orEmpty())
                        }
                        is Result.Success -> {
                            view.populate(it.value.text)
                        }
                    }
                }
        }
    }
}