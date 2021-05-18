package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsMutableStateFlow = MutableStateFlow<Fact?>(null)
    val catsStateFlow: StateFlow<Fact?> = _catsMutableStateFlow
    private val _errorMutableStateFlow = MutableStateFlow<String?>(null)
    val errorStateFlow: StateFlow<String?> = _errorMutableStateFlow
//    private val _catsLiveData = MutableLiveData<Fact>()
//    val catsLiveData: LiveData<Fact> = _catsLiveData

    init {
        catsRepository.listenForCatFacts().onEach { result->
            handleCatFactResult(result)
        }.launchIn(viewModelScope)


//            withContext(Dispatchers.Main) {
//                catsRepository.listenForCatFacts().collect {
//                    _catsLiveData.value = it
//                }
//            }
        }

    private fun handleCatFactResult(result: Result<Fact>) = when (result) {
        is Result.Error -> {
            // А как лучше поступать в случае, когда ошибка приходит как null?
            // Вообще, такое решение слишком примитивное, так как мы в реальном бою будем обрабатывать
            // еще коды ответа от сервера, которые что-то обозначают для нас (это будем делать в репо),
            // а текст ошибок будем брать из ресурсов (уже здесь)
            val errorMessage = "${result.cause}: ${result.cause.message.orEmpty()}"
            CrashMonitor.trackWarning(errorMessage)
            _errorMutableStateFlow.value = errorMessage
        }
        is Result.Success -> _catsMutableStateFlow.value = result.value
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}