package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.MutableCreationExtras
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()

    private val viewModelStoreOwner: ViewModelStoreOwner = this

    private val catsViewModel by lazy {
        ViewModelProvider.create(
            viewModelStoreOwner,
            factory = CatsViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(CatsViewModel.CATS_REPO, diContainer.repository)
            }
        )[CatsViewModel::class]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(catsViewModel) {
                    launch {
                        catsFlow.collect {
                            it?.let { cats ->
                                view.populate(cats)
                            }
                        }
                    }
                    launch {
                        eventErrorMessage.collect {
                            it?.let { msg ->
                                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}