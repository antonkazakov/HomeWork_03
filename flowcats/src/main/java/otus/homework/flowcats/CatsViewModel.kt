package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    val catsFlow: StateFlow<Result> = catsRepository.listenForCatFacts()
        .map {
            Success(it)
        }
        .catch {
            Error(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MLS),
            initialValue = Loading,
        )

    companion object {
        private const val STOP_TIMEOUT_MLS = 5_000L
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}