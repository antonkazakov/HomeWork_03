package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlowData = MutableStateFlow<Result>(Result.InitValue)
    val catsFlowData: StateFlow<Result> = _catsFlowData
    private val handler = CoroutineExceptionHandler { _, e ->
        Result.Error(e.message.toString())
    }

    init {
        viewModelScope.launch(handler) {
            catsRepository.listenForCatFacts()
                .catch { e ->
                    if (e is CancellationException) throw e
                    _catsFlowData.emit(Result.Error(e.message.toString()))
                }
                .collect { _catsFlowData.emit(Result.Success(it)) }
        }
    }

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}