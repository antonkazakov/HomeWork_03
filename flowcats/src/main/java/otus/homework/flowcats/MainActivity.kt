package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsLiveData.collect {
                    it.let { result ->
                        when (result) {
                            is Result.Idle -> {}
                            is Result.Error -> {
                                Log.e("Error", result.value.message ?: "Unknown error")
                                view.showError(resources.getString(R.string.network_error))
                            }
                            is Result.Success -> {
                                view.populate(result.data)
                            }
                        }
                    }
                }
            }
        }
    }
}