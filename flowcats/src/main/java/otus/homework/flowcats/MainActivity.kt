package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        scope.launch {
            catsViewModel.catsLiveData.filterNotNull().collect { result ->
                when(result) {
                    is Result.Success -> view.populate(result.fact)
                    is Result.Error -> view.showError(result.message)
                }
            }
        }
    }

    override fun onDestroy() {
        if (scope.isActive) scope.cancel()
        super.onDestroy()
    }
}