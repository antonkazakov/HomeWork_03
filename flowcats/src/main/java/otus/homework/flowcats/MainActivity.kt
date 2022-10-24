package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory(diContainer.repository)
    }

    lateinit var view: CatsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        /*catsViewModel.catsLiveData.observe(this){
            view.populate(it)
        }*/

        //view.viewModel = catsViewModel

        lifecycleScope.launchWhenStarted {
            catsViewModel.uiState.collect() {
                when (it) {
                    is Result.Loading -> {
                        // showProgress(...)
                    }
                    is Result.Success -> {
                        updateUi(it.data)
                    }
                    is Result.Error -> {
                        showError(it.message)
                    }
                }
            }
        }
    }

    private fun updateUi(fact: Fact) {
        view.populate(fact)
    }

    private fun showError(msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}