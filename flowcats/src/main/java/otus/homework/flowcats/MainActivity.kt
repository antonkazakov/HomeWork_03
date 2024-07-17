package otus.homework.flowcats

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory(diContainer.repository)
    }
    private var view: CatsView? = null

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            println("tagtag onStart thread: ${Thread.currentThread().name} dispatcher: ${coroutineContext[CoroutineDispatcher]}")
            catsViewModel.catsFlow.collect { result ->
                println("tagtag collect populate thread: ${Thread.currentThread().name} dispatcher: ${kotlin.coroutines.coroutineContext[CoroutineDispatcher]}")
                view?.populate(result)
            }
        }

    }

    override fun onStop() {
        println("tagtag onStop")
        lifecycleScope.coroutineContext.cancelChildren()
        super.onStop()
    }

    override fun onDestroy() {
        println("tagtag onDestroy")
        lifecycleScope.cancel()
        view = null
        super.onDestroy()
    }
}