package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsData.collect {
                    when(it) {
                        is Result.Error -> {
                            Toast.makeText(this@MainActivity, "${it.tr.message}", Toast.LENGTH_LONG).show()
                        }
                        is Result.Success -> {
                            view.populate(it.fact)
                        }

                        Result.Initial -> {}
                    }
            }
        }
    }
}