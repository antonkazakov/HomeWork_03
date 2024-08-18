package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

//        catsViewModel.catsLiveData.observe(this){
//            view.populate(it)
//        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsFlowData.collect() {
                    Log.d("${this.javaClass}", "$it")
                    when(it) {
                        is Success -> {
                            Log.d("${this.javaClass}", "${it.fact}")
                            view.populate(it.fact)
                        }
                        is Error -> {
                            Log.d("${this.javaClass}", "${it.message}")
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
                        }
                        else -> { Log.e("${this.javaClass}", "wrong way") }
                    }
                }
            }
        }
    }
}

sealed class Result
data class Success(val fact: Fact) : Result()
data class Error(val message: String?) : Result()