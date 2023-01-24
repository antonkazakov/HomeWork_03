package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        /*catsViewModel.catsLiveData.observe(this){
            view.populate(it)
        }*/

        lifecycleScope.launch {
            lifecycle.whenStarted {
                catsViewModel.stateFlow.collect { result ->
                    when (result) {
                        is Result.Success -> {
                            view.populate(result.item)
                        }
                        is Result.Initial -> {

                        }
                        is Result.Error -> {
                            Toast.makeText(this@MainActivity, result.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }
            }
        }
    }
}