package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    private lateinit var catsView: CatsView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catsView = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(catsView)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsStateFlow.collect() {
                    handleState(it)
                }
            }
        }
    }

    fun handleState(state: CatsViewModel.State) {
        when (state) {
            is CatsViewModel.Loading ->
                Toast.makeText(this, "progress bar", Toast.LENGTH_SHORT).show()
            // show progress bar
            is CatsViewModel.Success ->
                catsView.populate(state.factAndPicture)
            is CatsViewModel.Error ->
                Toast.makeText(this, state.message ?: "непонятная ошибка", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}