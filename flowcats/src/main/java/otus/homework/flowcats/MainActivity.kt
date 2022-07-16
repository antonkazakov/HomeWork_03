package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private val activityScope = ActivityScope()
    private lateinit var view: CatsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()

        activityScope.launch {
            catsViewModel.uiState.collect {
                it?.let { result ->
                    when (result) {
                        is Error -> {
                            Toast.makeText(
                                this@MainActivity,
                                result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Success<*> -> {
                            when (result.data) {
                                is Fact -> view.populate(result.data)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        activityScope.cancel()
    }
}

class ActivityScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main + CoroutineName("CatsCoroutine")
}