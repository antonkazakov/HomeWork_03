package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private val scope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)



        scope.launch {
            catsViewModel.catsData
                .collect { state ->
                    withContext(Dispatchers.Main) {
                        when (state) {
                            is Result.Success<*> -> {
                                view.progressOff()
                                view.populate(fact = state.valueCats as Fact)
                            }

                            is Result.Error -> {
                                Toast.makeText(this@MainActivity," Error ${state.error}", Toast.LENGTH_LONG).show()
                            }
                            Result.Init -> {
                                view.progressOn()
                            }
                        }
                    }
                }
        }
    }


    override fun onStop() {
        if (isFinishing) {
            scope.cancel("Stop PresenterScope in Activity")
        }
        super.onStop()
    }
}

