package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import otus.homework.flowcats.Result.Error
import otus.homework.flowcats.Result.Success

class CatsViewModel(
    private val catsRepository : CatsRepository,
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result<Fact>?>(null)
    val catsStateFlow : StateFlow<Result<Fact>?> = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository
                .listenForCatFacts()
                .map { fact -> Success(fact) }
                .catch { e ->
                    if (e is CancellationException) throw e

                    _catsStateFlow.tryEmit(Error(e.message ?: "Unknown error"))
                }
                .collect { _catsStateFlow.value = it }
        }
    }
}

class CatsViewModelFactory(private val catsRepository : CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T =
        CatsViewModel(catsRepository) as T
}

sealed class Result<T> {
    data class Success<T>(val data : T) : Result<T>()
    data class Error<T>(val message : String) : Result<T>()
}
