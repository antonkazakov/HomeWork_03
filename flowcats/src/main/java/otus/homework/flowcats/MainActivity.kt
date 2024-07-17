package otus.homework.flowcats

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private var view: CatsView? = null

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            catsViewModel.catsFlow.collect { result ->
                view?.populate(result)
            }
        }

    }

    override fun onStop() {
        lifecycleScope.coroutineContext.cancelChildren()
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleScope.cancel()
        view = null
        super.onDestroy()
    }
}