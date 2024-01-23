package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    // private val scope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsData
                    .collect { state ->
                        withContext(Dispatchers.Main) {
                            when (state) {
                                is Result.Success<*> -> {
                                    view.progressOff()
                                    view.populate(fact = state.valueCats as Fact)
                                }

                                is Result.Error -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        " Error ${state.error}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                Result.Init -> {
                                    view.progressOn()
                                }
                            }
                        }
                    }
            }
        }
    }

    /*override fun onStop() {
       *//* if (isFinishing) {
            scope.cancel("Stop PresenterScope in Activity")
        }*//*
        super.onStop()
    }*/
}

