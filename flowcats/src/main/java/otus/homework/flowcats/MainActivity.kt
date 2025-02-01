package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        val catsViewModel: CatsViewModel = ViewModelProvider.create(this,
            factory = CatsViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(CatsViewModel.REPOSITORY_KEY, diContainer.repository)
            }
        ) [CatsViewModel::class]
        lifecycleScope.launch {
            catsViewModel.catsFlow.collect {
                if (it != null) {
                    view.populate(it)
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.could_not_get_response_from_server),
            Toast.LENGTH_SHORT)
            .show()
    }
}
