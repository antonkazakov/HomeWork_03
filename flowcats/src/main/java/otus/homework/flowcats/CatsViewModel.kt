package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData

    private val _catsStateFlow: MutableStateFlow<Result<Fact>> = MutableStateFlow(Result.InitState)
    val catsStateFlow: StateFlow<Result<Fact>> = _catsStateFlow

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _catsLiveData.postValue(it)
                    _catsStateFlow.emit(Result.Success(it))
                }
            } catch (e: Exception) {
                _catsStateFlow.emit(Result.Error(e.message))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}