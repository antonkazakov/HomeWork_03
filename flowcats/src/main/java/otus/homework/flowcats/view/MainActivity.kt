package otus.homework.flowcats.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import otus.homework.flowcats.R
import otus.homework.flowcats.utils.DiContainer
import otus.homework.flowcats.utils.Result
import otus.homework.flowcats.viewmodel.CatsViewModel
import otus.homework.flowcats.viewmodel.CatsViewModelFactory

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsFlow.collect {
                when (it) {
                    is Result.Success -> {
                        view.setFactToView(it.value)
                        Log.i("!!!", "onCreate: ${it.value}")
                    }
                    is Result.Error -> Log.i("!!!", "onCreate: Error")
                }
            }
        }
    }
}