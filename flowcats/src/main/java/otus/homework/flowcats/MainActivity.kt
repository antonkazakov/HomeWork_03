package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import otus.homework.flowcats.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory(diContainer.repository)
    }
    private val mBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        catsViewModel.catsState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        catsViewModel.events
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEvents)
            .launchIn(lifecycleScope)
    }

    private fun render(state: CatsState) = with(mBinding) {
        factTextView.text = state.fact?.text ?: ""
    }

    private fun handleEvents(event: CatsEvent) {
        when (event) {
            is CatsEvent.ShowError -> showError(event.type)
        }
    }

    private fun showError(errorType: Result.ErrorType) {
        when (errorType) {
            Result.ErrorType.UnknownError -> toast("Unknown error")
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}