package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
            catsViewModel.catsStateFlow.collect {
                when (it) {
                    is Result.Success -> it.value?.let { fact -> view.populate(fact) }
                    is Result.Initial -> Toast.makeText(this@MainActivity, it.initialMessage, Toast.LENGTH_SHORT)
                        .show()
                    is Result.Error -> Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }
}