package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData

    private val _catsStateFlow = MutableStateFlow<Result>(Result.Error("Load facts"))
    val catsStateFlow: StateFlow<Result> = _catsStateFlow.asStateFlow()

    fun getCatsToLiveData() {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .flowOn(Dispatchers.IO)
                .catch { }
                .collect { _catsLiveData.value = it }
        }
    }

    fun getCatsToStateFlow() {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .flowOn(Dispatchers.IO)
                .catch { throwable: Throwable ->
                    _catsStateFlow.value = Result.Error(throwable.message?:"Ошибка загрузки данных")
                }
                .collect { _catsStateFlow.value = Result.Success(it) }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result {
    data class Success(val fact: Fact) : Result()
    data class Error(val errorMessage: String) : Result()
}