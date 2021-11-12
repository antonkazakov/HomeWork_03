package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CatsViewModel(catsRepository: CatsRepository) : ViewModel() {
    val catImageState: StateFlow<Result<String>> = catsRepository.listenForCatImages()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = Result.Success(EMPTY_STRING)
        )

    private companion object {
        const val EMPTY_STRING = ""
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}