package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    val catsStateFlow = catsRepository.listenForCatFacts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT),
            initialValue = Result.Error(EMPTY_INIT_VALUE)
        )

    companion object {
        private const val STOP_TIMEOUT = 5_000L
        private const val EMPTY_INIT_VALUE = ""
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}