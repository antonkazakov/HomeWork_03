package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.model.Result

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        job = lifecycleScope.launch {
            catsViewModel.catsStateFlow.collect {
                when (it) {
                    is Result.Success -> view.populate(it.fact as Fact)
                    is Result.Error -> Toast.makeText(
                        applicationContext,
                        it.throwable.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}