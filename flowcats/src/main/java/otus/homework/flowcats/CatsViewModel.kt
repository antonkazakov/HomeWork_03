package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Проблемы"
    }

    val uiState: StateFlow<Result<Fact>>
        get() = _uiState
    private val _uiState: MutableStateFlow<Result<Fact>> = MutableStateFlow(Result.Loading)

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    catsRepository.listenForCatFacts().collect {
                        _uiState.tryEmit(Result.Success(it))
                    }
                } catch (e: Exception) {
                    _uiState.tryEmit(Result.Error(e.message ?: DEFAULT_ERROR_MESSAGE))
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}