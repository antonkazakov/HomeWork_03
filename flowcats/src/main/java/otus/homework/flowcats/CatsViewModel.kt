package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onErrorResumeNext
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.launch

sealed interface CatsUiState<T> {
    class Loading<T> : CatsUiState<T>
    data class Common<T>(val data: T) : CatsUiState<T>
    data class Error<T>(val message: String) : CatsUiState<T>
}

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow<CatsUiState<Fact>>(CatsUiState.Loading())
    val catsState: StateFlow<CatsUiState<Fact>> get() = _catsState

    init {
        viewModelScope.launch {
            catsRepository
                .listenForCatFacts()
                .map { result ->
                    when (result) {
                        is Result.Error -> CatsUiState.Error(result.exception.message.orEmpty())
                        is Result.Success -> CatsUiState.Common(result.data)
                    }
                }
                .collect(_catsState::emit)
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}