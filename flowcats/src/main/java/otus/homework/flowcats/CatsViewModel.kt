package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
            var catFactsFlow: Flow<Fact>? = null
            withContext(Dispatchers.IO) {
                try {
                    catFactsFlow = catsRepository.listenForCatFacts()
                } catch (e: Exception) {
                    catsStateFlow.value = e.message?.let { Result.Error(it) }
                }
            }
            withContext(Dispatchers.Main) {
                catFactsFlow?.collect {
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