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

        lifecycleScope.launchWhenResumed {
            catsViewModel.catsStateFlow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        view.populate(result.data)
                    }
                    is Result.Error -> {
                        view.showToast(result.errorMessage)
                    }
                    is Result.Initial -> {}
                }
            }
        }
    }
}