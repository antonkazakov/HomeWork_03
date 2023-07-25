package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.Result.Error
import otus.homework.flowcats.Result.Success

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

         lifecycleScope.launch {
             lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                 catsViewModel.catsFact.collect { result ->
                     when(result) {
                         is Success -> view.populate(result.catsModel)
                         is Error -> showToastException(exceptions = result.error)
                     }
                 }
             }
        }
    }

    private fun showToastException(exceptions: Throwable?) {
        val message = exceptions
            ?.toString()
            ?.substringAfter(delimiter = this.getString(R.string.substring_for_exception))
            .orEmpty()

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
