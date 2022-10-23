package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    /*private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData*/
    private val _uiState = MutableStateFlow<Result<Fact>>(Result.Loading("loading..."))
    val uiState: StateFlow<Result<Fact>> = _uiState

    private val handler = CoroutineExceptionHandler { _, exception ->
        //CrashMonitor.trackWarning(exception.toString())
        _uiState.value = Result.Error(exception.toString())
    }

    init {
        viewModelScope.launch(handler) {
            try {
                catsRepository.listenForCatFacts()
                    .flowOn(Dispatchers.IO)
                    .collect { fact ->
                        _uiState.value = Result.Success(fact)
                    }
            } catch (e: java.net.SocketTimeoutException) {
                _uiState.value = Result.Error("Не удалось получить ответ от сервера")
            }
            /*catch (e: retrofit2.HttpException) {
                _uiState.value = Result.Error("404")
            }*/
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}