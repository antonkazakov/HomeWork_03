package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    lateinit var catsFlow: StateFlow<Result<Fact>>

    init {
        viewModelScope.launch {
            catsFlow = catsRepository.listenForCatFacts()
                .map { Result.Success(it) as Result<Fact> }
                .catch { emit(Result.Error(it)) } // is not working
                .stateIn(
                    scope = this,
                    started = SharingStarted.Eagerly,
                    initialValue = Result.Success(Fact.EMPTY)
                )
        }
    }

    sealed class Result<T> {
        data class Success<T>(val value: T) : Result<T>()
        data class Error<T>(val error: Throwable) : Result<T>()
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}