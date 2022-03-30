package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsViewModel.catsFlow.launchWhenStarted(lifecycleScope) { result ->
            stateManagement(result, view)
        }
    }

    private fun stateManagement(result: Result<Fact>, view: CatsView) {
        when (result) {
            is Result.Loading -> showLoadingState()
            is Result.Success -> showNormalState(result.data, view)
            is Result.Error -> showErrorState(result.error)
        }
    }

    private fun showLoadingState() {
        // TODO()
    }

    private fun showNormalState(item: Fact, view: CatsView) {
        view.populate(item)
    }

    private fun showErrorState(error: Throwable) {
        // TODO()
    }
}