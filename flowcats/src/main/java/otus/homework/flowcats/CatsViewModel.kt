package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CatsUiState> =
        MutableStateFlow(CatsUiState.Success(CatFact("", 0)))
    val uiState: StateFlow<CatsUiState> = _uiState

    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            _uiState.value = CatsUiState.Error("CoroutineExceptionHandler got $it")
        }
    }

    init {
        viewModelScope.launch(handler) {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts().collect { fact ->
                    _uiState.value = CatsUiState.Success(fact)
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
