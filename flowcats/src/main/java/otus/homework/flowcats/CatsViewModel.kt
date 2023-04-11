package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    sealed class Result {
        class Success<T>(val data: T) : Result()
        class Error(val throwable: Throwable) : Result()
    }

    private val _result = MutableStateFlow<Result?>(null)
    val resultObservable = _result.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    withContext(Dispatchers.IO) {
                        _result.value = Result.Success(it)
                    }
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is CancellationException -> {
                        throw throwable
                    }
                    else -> {
                        _result.value = Result.Error(throwable)
                    }
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