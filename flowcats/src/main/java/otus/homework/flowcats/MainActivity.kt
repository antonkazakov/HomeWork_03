package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.handler.Result
import otus.homework.flowcats.handler.ResultHandler

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private val resultHandler = ResultHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        initFlowCollector(view)

        view.setListeningCallbacks(
            startCallback = ::startListening,
            stopCallback = ::stopListening
        )
    }

    private fun initFlowCollector(catsView: CatsView) {
        lifecycleScope.launch {
            catsViewModel.catsDataFlow
                .collect { result ->
                    resultHandler.onResult(result)
                    when (result) {
                        is Result.Success -> catsView.populate(result.data as Fact)
                        is Result.Error -> {}
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        startListening()
    }

    override fun onPause() {
        super.onPause()
        stopListening()
    }

    private fun startListening() {
        catsViewModel.startListening()
    }

    private fun stopListening() {
        catsViewModel.stopListening()
    }
}