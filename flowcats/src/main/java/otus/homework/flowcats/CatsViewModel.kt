package otus.homework.flowcats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData

    /** State экрана */
    val screenState: StateFlow<Result<Fact?>?> get() = _screenState.asStateFlow()
    private val _screenState = MutableStateFlow<Result<Fact?>?>(null)

    init {
        viewModelScope.launch {
//                catsRepository.listenForCatFacts().collect {
//                    _catsLiveData.postValue(it)
//                }
            try {
                catsRepository.listenForCatFacts().collect { _screenState.emit(Result.Success(it)) }
            } catch (e: Throwable) {
                _screenState.emit(Result.Error(e.message))
            }
        }
    }
}

class CatsViewModelFactory(
    private val catsRepository: CatsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatsViewModel(catsRepository) as T
    }
}