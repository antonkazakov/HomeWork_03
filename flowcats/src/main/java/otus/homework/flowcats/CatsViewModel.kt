package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result<Fact>>(Result.Empty)
    val catsStateFlow: StateFlow<Result<Fact>> = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository
                .listenForCatFacts()
                .flowOn(Dispatchers.IO)
                .catch {
                    val message = when (it) {
                        is SocketTimeoutException -> "Failed to get a response from the server"
                        is UnknownHostException -> "Check internet connection"
                        else -> it.message ?: ""
                    }
                    _catsStateFlow.emit(Result.Error(message))
                }
                .collect { _catsStateFlow.emit(Result.Success(it)) }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}