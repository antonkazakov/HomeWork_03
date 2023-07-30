package otus.homework.flowcats.presentation.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import otus.homework.flowcats.R
import otus.homework.flowcats.di.DiContainer
import otus.homework.flowcats.presentation.CatsView
import otus.homework.flowcats.presentation.ErrorDialogFragment

/**
 * `Activity` с `custom view` информации о коте, построенная на основе `ViewModel`
 * со стандартным обновление через `MutableStateFlow`
 */
class CatsActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModel.provideFactory(diContainer.repository)
    }

    private lateinit var catsView: CatsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catsView = layoutInflater.inflate(R.layout.activity_cats_base, null) as CatsView
        setContentView(catsView)
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            catsViewModel.uiState.flowWithLifecycle(lifecycle).collect { renderUi(it) }
        }
    }

    private fun renderUi(result: CatUiState) {
        when (result) {
            is CatUiState.Idle -> catsView.reset()
            is CatUiState.Success -> catsView.populate(result.cat)
            is CatUiState.Error -> onError(result)
        }
    }

    private fun onError(result: CatUiState.Error) {
        catsView.warn(result.message)
        if (!result.isShown) {
            ErrorDialogFragment.newInstance(result.message).show(supportFragmentManager, null)
            catsViewModel.onErrorShown()
        }
    }
}