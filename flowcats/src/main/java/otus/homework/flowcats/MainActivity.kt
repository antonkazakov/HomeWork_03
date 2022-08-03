package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

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
        lifecycleScope.launchWhenCreated {
            catsViewModel.screenState
                .collect {
                    when (it) {
                        is Result.Error -> {
                            view.showToast(it.message)
                        }
                        is Result.Success -> {
                            it.result?.let { it1 -> view.populate(it1) }
                        }
                        else -> {}
                    }
                }
        }
    }
}