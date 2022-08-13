package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        observeViewModel(view)
    }

    private fun observeViewModel(view: CatsView) {
        observeCatFacts(view)
    }

    private fun observeCatFacts(view: CatsView) {
        catsViewModel
            .getCatsStateFlow()
            .filterNotNull()
            .onEach {
                when(it){
                    is Result.Success<*> -> view.populate(it.value as Fact)
                    is Result.Error -> view.showToast(it.message)
                }
            }.launchWhenStarted(lifecycleScope)
    }
}