package otus.homework.flowcats.presentation.base

import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import otus.homework.flowcats.domain.CatsRepository

/**
 * [ViewModel] получения информации о коте, использующая стандартное обновление через [MutableStateFlow]
 *
 * @param repository репозиторий информации о кошке
 */
class CatsViewModel(
    private val repository: CatsRepository
) : ViewModel() {

    /** UI состояние информации о коте */
    val uiState: Flow<CatUiState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow<CatUiState>(CatUiState.Idle)

    private val handler = CoroutineExceptionHandler { _, e -> onError(e) }

    init {
        viewModelScope.launch(handler) {
            try {
                repository.listenForCatFacts()
                    .collect { cat -> _uiState.update { CatUiState.Success(cat) } }
            } catch (e: Exception) {
                onError(e)
                if (e is CancellationException) throw e
            }
        }
    }

    private fun onError(e: Throwable) {
        _uiState.update { CatUiState.Error(e.toString(), false) }
    }

    /** Обработать отображение ошибки */
    fun onErrorShown() {
        _uiState.update { state -> if (state is CatUiState.Error) state.copy(isShown = true) else state }
    }

    companion object {

        /**
         * Получить фабрику по созданию [CatsViewModel].
         *
         * @param repository репозиторий информации о кошке
         */
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(repository: CatsRepository) =
            object : ViewModelProvider.NewInstanceFactory() {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    CatsViewModel(repository) as T
            }
    }
}

