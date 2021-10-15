package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableStateFlow<Result?>(null)
    val catsLiveData = _catsLiveData.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository
                .listenForCatFacts()
                .retry {
                    Log.e("CatsViewModel", "caught exception: $it", it)
                    if (it is SocketTimeoutException) {
                        _catsLiveData.emit(Error(message = "could not fetch server response"))
                        true
                    } else {
                        false
                    }
                }
                    // catch errors in case retry was not used
                .catch  { throwable ->
                    Log.e("CatsViewModel", "caught exception: $throwable", throwable)
                    _catsLiveData.emit(Error(message = "stop trying to get server response"))
                }
                .collect {
                    _catsLiveData.value = Success(it)
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
data class Success<T>(val result: T) : Result()
data class Error(val message: String) : Result()
