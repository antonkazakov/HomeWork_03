package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        val progress: ProgressBar = view.findViewById(R.id.progressbar)
        val factTextView: TextView = view.findViewById(R.id.fact_textView)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catFactFlow.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            progress.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            progress.visibility = View.GONE
                            view.populate(result.fact)
                        }
                        is Result.Error -> {
                            progress.visibility = View.GONE
                            factTextView.text = result.exception.message
                        }
                    }
                }
            }
        }
    }
}