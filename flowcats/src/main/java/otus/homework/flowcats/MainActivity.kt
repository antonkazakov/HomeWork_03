package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenResumed {
            catsViewModel.catsStateFlow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        result.fact?.let { view.populate(it) }
                    }
                    is Result.Error -> {
                        Log.d("MainActivity", result.throwable.message.toString())
                    }
                    else -> {
                        throw RuntimeException("Wrong data received")
                    }
                }
            }
        }
    }
}