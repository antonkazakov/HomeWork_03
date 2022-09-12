package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsStateFlowData.collect {
                when (it) {
                    is Result.Error -> {
                        Toast.makeText(applicationContext, it.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Result.Success<*> -> {
                        if (it.data is Fact) {
                            view.populate(it.data)
                        }
                    }
                }
            }
        }
    }
}