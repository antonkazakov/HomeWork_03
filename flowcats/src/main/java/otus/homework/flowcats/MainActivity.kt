package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsStateFlow.collectLatest {
                when(it) {
                    is Result.Success -> view.populate(it.fact)
                    is Result.Error -> Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
                    else -> { /*no-op*/ }
                }
            }
        }
    }
}