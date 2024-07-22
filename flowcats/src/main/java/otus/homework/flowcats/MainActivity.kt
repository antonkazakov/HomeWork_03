package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val catsViewModel: CatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as? CatsView
        setContentView(view)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsData.collect { result ->
                    when (result) {
                        is Success -> view?.populate(result.fact)
                        is Error -> showException(exceptions = result.throwable)
                        Initial -> {}
                    }
                }
            }
        }
    }

    private fun showException(exceptions: Throwable?) {
        val message = exceptions
            ?.toString()
            ?.substringAfter(delimiter = this.getString(R.string.exception))
            .orEmpty()

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}