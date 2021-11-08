package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        lifecycleScope.launch {
            catsViewModel.state.collect {
                when(it) {
                    is CatsViewModel.State.IsLoading -> Unit
                    is CatsViewModel.State.ShowFact -> view.populate(it.fact)
                    is CatsViewModel.State.ShowError ->
                        Toast.makeText(
                            this@MainActivity,
                            it.error.message ?: "",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }
}