package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        lifecycleScope.launchWhenCreated{
            catsViewModel.catsFlow.collect {
                when(it){
                    //is Loading -> {}
                    is Error -> Toast.makeText(this@MainActivity, R.string.server_error, Toast.LENGTH_LONG).show()
                    is Success -> view.populate(it.fact)
                }

            }
        }

    }
}