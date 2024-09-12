package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.Result.Error
import otus.homework.flowcats.Result.Success

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        initObservers(view)
        setContentView(view)
    }

    private fun initObservers(view: CatsView) {
        lifecycleScope.launchWhenResumed {
            catsViewModel.getCatFacts()
                .collect { result ->
                when (result) {
                    is Success -> {
                        result.data?.let {
                            view.populate(it)
                        }
                    }
                    is Error -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                result.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}