package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel = CatsViewModel(diContainer.repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)


        lifecycleScope.launch {
            catsViewModel.catsStateFlow.collect {
                it?.let {
                    when (it) {
                        is Result.Success -> view.populate(it.fact)
                        is Result.Error -> view.showError(it.message)
                    }
                }
            }
        }
    }
}