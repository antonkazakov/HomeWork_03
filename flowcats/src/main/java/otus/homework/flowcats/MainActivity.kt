package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        CoroutineScope(Dispatchers.IO).launch {
            catsViewModel.catsLiveData.collect { fact ->
                fact?.let {
                    withContext(Dispatchers.Main) { view.populate(it) }
                }
            }
            catsViewModel.toastMessage.collect { message ->
                message?.let {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            it,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }
}