package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenResumed {
            catsViewModel.catsStF.collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> Toast.makeText(this@MainActivity, result.e.toString(), Toast.LENGTH_LONG).show()
                    ApiResult.Loading -> {}
                    is ApiResult.Success ->  view.populate(result.data)
                }

            }
        }

    }
}