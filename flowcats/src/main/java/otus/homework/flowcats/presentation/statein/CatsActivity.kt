package otus.homework.flowcats.presentation.statein

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import otus.homework.flowcats.R
import otus.homework.flowcats.di.DiContainer
import otus.homework.flowcats.presentation.CatsView
import otus.homework.flowcats.presentation.ErrorDialogFragment

/**
 * `Activity` с `custom view` информации о коте, построенная на основе `ViewModel`
 * с обновление `Flow` через `stateIn`
 */
class CatsActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModel.provideFactory(diContainer.repository)
    }

    private lateinit var catsView: CatsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catsView = layoutInflater.inflate(R.layout.activity_cats_state_in, null) as CatsView
        setContentView(catsView)
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.uiState.collect { renderUi(it) }
            }
        }
    }

    private fun renderUi(state: CatUiState) {
        when (state) {
            CatUiState.Idle -> catsView.reset()
            is CatUiState.Success -> catsView.populate(state.cat)
            is CatUiState.Error -> renderError(state)
        }
    }

    private fun renderError(state: CatUiState.Error) {
        val dialog = supportFragmentManager.findFragmentByTag(ERROR_DIALOG_TAG)
        if (dialog == null) {
            ErrorDialogFragment.newInstance(state.message)
                .show(supportFragmentManager, ERROR_DIALOG_TAG)
        }
        catsView.warn(state.message)
    }

    private companion object {
        const val ERROR_DIALOG_TAG =
            "otus.homework.flowcats.presentation.statein.ErrorDialogFragment"
    }
}