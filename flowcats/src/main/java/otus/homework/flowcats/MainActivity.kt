package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory(diContainer.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsFlow.collect {
                when (it) {
                    is Error -> {
                        Toast.makeText(
                            this@MainActivity, "${it.err.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Success<*> -> {
                        val fact = it.data
                        if (fact is Fact) {
                            view.populate(fact)
                        }
                    }
                    is Empty -> {
                    }
                }
            }
        }

        catsViewModel.catsLiveData.observe(this) {
            view.populate(it)
        }
    }
}