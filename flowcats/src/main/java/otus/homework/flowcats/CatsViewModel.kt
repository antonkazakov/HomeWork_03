package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val exceptionHandler =
        CoroutineExceptionHandler { _, error -> Result.Error(message = error.message.orEmpty()) }

    private val _catsStateFlow = MutableStateFlow<Result?>(null)
    val catsStateFlow: StateFlow<Result?> = _catsStateFlow

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch((exceptionHandler)) {
            withContext(Dispatchers.IO) {
                runCatching {
                    catsRepository.listenForCatFacts().collect {
                        _catsStateFlow.tryEmit(Result.Success(it))
                    }
                }.onFailure { error -> Result.Error(message = error.message.orEmpty()) }
            }
        }
    }

    class CatsViewModelFactory(private val catsRepository: CatsRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CatsViewModel(catsRepository) as T
    }
}