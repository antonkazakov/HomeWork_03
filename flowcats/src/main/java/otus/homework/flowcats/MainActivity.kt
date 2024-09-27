package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsStateFlow.collect { result ->
                    processCatFactResult(result, view)
                }
            }
        }
    }

    private fun processCatFactResult(result: Result<Fact>, view: CatsView) {
        when (result) {
            Result.Loading -> Unit
            is Result.Error -> Toast.makeText(applicationContext, result.message, Toast.LENGTH_SHORT).show()
            is Result.Success -> view.populate(result.data)
        }
    }
}