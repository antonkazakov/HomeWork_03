package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result>(Result.Loading)
    val catsFlow: StateFlow<Result> = _catsFlow

    init {
        viewModelScope.launch {
                catsRepository.listenForCatFacts()
                    .catch { ex ->
                       _catsFlow.value = Result.Error(ex.localizedMessage ?: "Unknown error")
                    }
                    .flowOn(Dispatchers.Main)
                    .collect {
                    _catsFlow.value = Result.Success(it)
                }
        }
    }
}

sealed class Result {
    data class Success<T>(val data: T) : Result()
    data class Error(val message : String) : Result()
    object Loading : Result()
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}