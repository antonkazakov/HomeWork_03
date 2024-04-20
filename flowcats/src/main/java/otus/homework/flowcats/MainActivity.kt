package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var catsView: CatsView

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory(diContainer.repository, ResultMapper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null)
        setContentView(view)

        catsView = findViewById(R.id.fact_textView)

        lifecycleScope.launch {
            catsViewModel.catsFlow.collect { result ->
                result.show(catsView)
            }
        }
    }
}