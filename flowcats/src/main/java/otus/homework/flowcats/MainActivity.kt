package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private val isUseLiveData = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        if(isUseLiveData) {
            catsViewModel.getCatsToLiveData()
            catsViewModel.catsLiveData.observe(this){
            view.populate(it)
            }
        } else {
            catsViewModel.getCatsToStateFlow()
            lifecycleScope.launchWhenStarted {
                catsViewModel.catsStateFlow.collect() {
                    when {
                        it is Result.Success -> view.populate(it.fact)
                        it is Result.Error -> view.showError(it.errorMessage)
                    }

                }
            }
        }
    }
}