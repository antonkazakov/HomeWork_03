package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.stateFlow.collect { state ->
                when (state) {
                    is CatsViewModel.State.Default -> view.populate(state.fact)
                    is CatsViewModel.State.Error -> view.showMessage(state.message)
                    is CatsViewModel.State.InProgress -> view.showMessage("In Progress")
                }
            }
        }
    }
}