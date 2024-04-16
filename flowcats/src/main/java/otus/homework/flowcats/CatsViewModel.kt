package otus.homework.flowcats

import androidx.lifecycle.*
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

    private var _catsStateFlow = MutableStateFlow<Result>(Result.Loading)
    val catsStateFlow: StateFlow<Result> get() = _catsStateFlow

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _catsStateFlow.value = Result.Error("${throwable.message}")
        }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            catsRepository.listenForCatFacts().collect {
                _catsStateFlow.value = Result.Success(it)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result {
    data class Success(val fact: Fact) : Result()
    data class Error(val message: String) : Result()
    object Loading : Result()
}