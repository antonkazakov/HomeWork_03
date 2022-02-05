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

    sealed interface State
    object Loading : State
    data class Success(val factAndPicture: Fact) : State
    data class Error(val message: String?) : State


    private val _catsStateFlow: MutableStateFlow<State> = MutableStateFlow(Loading)
    val catsStateFlow: StateFlow<State> = _catsStateFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    catsRepository.listenForCatFacts().collect {
                        _catsStateFlow.value = Success(it)
                    }
                } catch (e: Exception) {
                    _catsStateFlow.value = Error(e.message)
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