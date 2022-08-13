package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val catsStateFlow = MutableStateFlow<Result?>(null)

    init {
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    catsStateFlow.value = Result.Success(it)
                }
            }
        }
    }

    private fun getCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            catsStateFlow.value = throwable.message?.let { Result.Error(it) }
        }
    }

    internal fun getCatsStateFlow() = catsStateFlow.asStateFlow()
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}