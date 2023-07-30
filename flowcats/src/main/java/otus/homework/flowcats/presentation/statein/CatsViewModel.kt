package otus.homework.flowcats.presentation.statein

import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import otus.homework.flowcats.domain.CatsRepository

/**
 * [ViewModel] получения информации о коте, использующая `stateIn` с [Flow]
 *
 * @param repository репозиторий информации о кошке
 */
class CatsViewModel(
    repository: CatsRepository
) : ViewModel() {

    /** UI состояние информации о коте */
    val uiState: Flow<CatUiState> = repository.listenForCatFacts()
        .map { cat -> CatUiState.Success(cat) as CatUiState }
        .catch { e -> onError(e) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CatUiState.Idle
        )

    private suspend fun FlowCollector<CatUiState>.onError(e: Throwable) {
        emit(CatUiState.Error(e.toString()))
        if (e is CancellationException) throw e
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