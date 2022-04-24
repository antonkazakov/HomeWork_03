package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(Initial)
    val uiState: StateFlow<State> = _uiState

    //private val _catsLiveData = MutableLiveData<Fact>()
    //val catsLiveData: LiveData<Fact> = _catsLiveData

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts().collect {
                    when(it) {
                        is Fact -> _uiState.value = Success(it as Fact)
                        is Exception -> _uiState.value = it.message?.let { it1 -> ErrorCat(it1) }!!
                    }
                    //_catsLiveData.value = it
                }
            }
        }
    }

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class State()
object Initial: State()
data class Success(val item: Fact) : State()
class ErrorCat(val message: String) : State()