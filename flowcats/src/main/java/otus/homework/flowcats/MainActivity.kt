package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

//        catsViewModel.catsLiveData.observe(this){
//            view.populate(it)
//        }

        lifecycleScope.launchWhenStarted {
            subscribeOnFacts(view)
            subscribeOnErrors(view)
        }
    }

    private fun CoroutineScope.subscribeOnErrors(view: CatsView) {
        catsViewModel.errorStateFlow.onEach { error ->
            error?.let {
                view.showMessage(it)
            }
        }.launchIn(this)
    }

    private fun CoroutineScope.subscribeOnFacts(view: CatsView) {
        catsViewModel.catsStateFlow.onEach { fact ->
            fact?.let { view.populate(it) }
        }.launchIn(this)
    }
}