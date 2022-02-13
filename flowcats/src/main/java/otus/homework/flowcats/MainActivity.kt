package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        lifecycleScope.launchWhenCreated {
            catsViewModel.catsLiveData.collect {
                when (it) {
                    is Error -> Toast.makeText(this@MainActivity,"Error", Toast.LENGTH_SHORT)
                        .show()
                    is Success -> it.result?.let { fact -> view.populate(fact) }
                }

            }
        }
    }
}