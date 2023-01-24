package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository,
) : ViewModel() {

    /*private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData*/

    private val _stateFlow = MutableStateFlow<Result>(Result.Initial)
    val stateFlow: StateFlow<Result> = _stateFlow.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect { fact ->
                    _stateFlow.emit(Result.Success(fact))
                }
            } catch (e: Exception) {
                _stateFlow.emit(Result.Error(e.stackTraceToString()))
            }
        }
    }


}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result {
    object Initial : Result()
    class Success(val item: Fact) : Result()
    class Error(val message: String) : Result()
}