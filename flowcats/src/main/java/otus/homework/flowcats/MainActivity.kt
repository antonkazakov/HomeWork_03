package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import kotlin.math.log

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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
               catsViewModel.catsStateFlow.collect {
                   it.data?.let { fact -> view.populate(fact) }
               }
            }
        }
    }
}