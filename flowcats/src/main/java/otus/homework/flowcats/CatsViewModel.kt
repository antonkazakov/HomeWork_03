package otus.homework.flowcats

import androidx.lifecycle.*
import java.lang.Exception
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> get() = _uiState

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect { result ->
                    when (result) {
                        is Result.Success -> _uiState.value = UiState.Success(result.value)
                        is Result.Error -> _uiState.value = UiState.Error(result.throwable)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class UiState {
    object Initial : UiState()
    data class Success(val fact: Fact) : UiState()
    data class Error(val throwable: Throwable) : UiState()
}