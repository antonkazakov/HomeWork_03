package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

//    private val _catsLiveData = MutableLiveData<Fact>()
//    val catsLiveData: LiveData<Fact> = _catsLiveData
    private val _catsStateFlow = MutableStateFlow<CatsState>(Initial)
    val catsStateFlow: StateFlow<CatsState> = _catsStateFlow

    init {
/*
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    //_catsLiveData.value = it // error
                    _catsLiveData.postValue(it)
                }
            }
        }
*/
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _catsStateFlow.emit(Success(it))
                }
            }
            catch (e:Exception) {
                _catsStateFlow.emit(Error(e.message ?: ""))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenStarted {
        this@launchWhenStarted.collect()
    }
}