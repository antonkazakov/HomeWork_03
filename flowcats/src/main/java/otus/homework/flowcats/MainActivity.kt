package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsState.collect {
                when (it) {
                    is Result.StartLoading -> {
                        showProgressBar()
                    }
                    is Result.Success -> {
                        view.populate(it.value)
                    }
                    is Result.Error -> {
                        Toast.makeText(this@MainActivity,
                            "произола ошибка ${it.throwable?.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun showProgressBar() {

    }
}