package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.data.CatsRepository
import otus.homework.flowcats.data.Fact
import otus.homework.flowcats.data.FactUiState
import java.lang.Exception


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<FactUiState> = MutableStateFlow(FactUiState.Loading)
    val uiState: StateFlow<FactUiState> = _uiState

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { ex -> _uiState.value = FactUiState.Error(ex) }
                .flowOn(Dispatchers.IO)
                .onEach {
                    _uiState.value = FactUiState.Loading
                }
                .flowOn(Dispatchers.Main)
                .collect { fact ->
                    _uiState.value = FactUiState.Success(fact)
                }
        }
    }


    class CatsViewModelFactory(private val catsRepository: CatsRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            CatsViewModel(catsRepository) as T
    }
}
