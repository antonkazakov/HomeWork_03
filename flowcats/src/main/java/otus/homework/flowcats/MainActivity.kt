package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        lifecycleScope.launch {
            launch {
                catsViewModel.cats.filterNotNull().collect {
                    view.populate(it)
                }
            }
            launch {
                catsViewModel.error.filterNotNull().collect {
                    Toast.makeText(this@MainActivity, it.message ?: "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}