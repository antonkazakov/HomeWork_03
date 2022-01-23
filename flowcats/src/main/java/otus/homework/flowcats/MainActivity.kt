package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenCreated {
            catsViewModel.catsStateFlow.collect {
                Log.d("MainActivity", "thread: ${Thread.currentThread().name}")
                when (it) {
                    is Success<*> ->
                        if (it.value is Fact) {
                            view.populate(it.value)
                        } else {
                            Toast.makeText(this@MainActivity, "Not cat fact received", Toast.LENGTH_LONG).show()
                        }
                    is Error -> {
                        Toast.makeText(this@MainActivity, "Error received ${it.message}", Toast.LENGTH_LONG).show()
                    }
                    is Loading -> {
                        // ждём
                    }
                }
            }
        }
    }
}