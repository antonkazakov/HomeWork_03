package otus.homework.flowcats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.domain.CatsRepository
import otus.homework.flowcats.models.domain.Fact
import otus.homework.flowcats.models.domain.Result

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<Result>(Result.Success(Fact("")))
    val uiState: StateFlow<Result> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.value = Result.Error(throwable)
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            catsRepository.listenForCatFacts().collect { it: Fact ->
                _uiState.value = Result.Success(it)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}