package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    private lateinit var view: CatsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.stateFlow.collect { state ->
                render(state)
            }
        }
    }

    private fun render(state: State) {
        when (state) {
            is State.Data -> {
                view.populate(state.fact)
            }
            is State.Error -> {
                Toast.makeText(this, state.ex.message, Toast.LENGTH_SHORT).show()
            }
            is State.Init -> {}
        }
    }
}