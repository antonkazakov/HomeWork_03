package otus.homework.flowcats

import android.util.Log
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

    private val _catsFlow = MutableStateFlow<Result>(Empty)
    val catsFlow: StateFlow<Result> = _catsFlow

    private val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("TAG", throwable.message.toString())
    }

    init {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    catsRepository.listenForCatFacts().collect {
                        _catsFlow.emit(Success(it.first()))
                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", t.message.toString())
                _catsFlow.emit(Error(t))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result
data class Error(val t: Throwable?) : Result()
object Empty : Result()
data class Success<T> (val data: T) : Result()