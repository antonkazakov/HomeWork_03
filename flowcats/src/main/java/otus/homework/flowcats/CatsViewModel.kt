package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.model.Error
import otus.homework.flowcats.model.Result
import otus.homework.flowcats.model.Success
import java.util.concurrent.TimeoutException

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> handleException(throwable) }

    val flow = MutableStateFlow<Result<*>>(Error(TimeoutException("nothing")))

    init {
        viewModelScope.launch(exceptionHandler) {
            catsRepository.listenForCatFacts().collect {
                flow.tryEmit(Success(it))
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        flow.tryEmit(Error(throwable))
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}