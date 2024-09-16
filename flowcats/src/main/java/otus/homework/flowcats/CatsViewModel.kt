package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData

    private val _catsStateFlow = MutableStateFlow<Result<Fact>>(Result.Success(null))
    val catsStateFlow: StateFlow<Result<Fact>> = _catsStateFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    catsRepository.listenForCatFacts().collect {
                        _catsStateFlow.value = Result.Success(it)
                    }
                } catch (e: Exception) {
                    catsRepository.listenForCatFacts().collect {
                        _catsStateFlow.value = Result.Error(it)
                    }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatsViewModel(catsRepository) as T
    }
}