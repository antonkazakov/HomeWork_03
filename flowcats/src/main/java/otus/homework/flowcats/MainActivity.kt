package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private val catsStateFlowViewModel by viewModels<CatsStateFlowViewModel> { CatsStateFLowViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        //LiveData
//        catsViewModel.catsLiveData.observe(this){
//            view.populate(it)
//        }

        //StateFlow
        lifecycleScope.launch {
            catsStateFlowViewModel.catsStateFlow.collect { catsStateFlow ->
                when (catsStateFlow) {
                    is Result.Success -> view.populate(catsStateFlow.data)
                    is Result.Error -> Log.i("catsStateFlow", "error ${catsStateFlow.exception}")
                }

            }
        }
    }
}