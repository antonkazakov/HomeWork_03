package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    sealed class State {
        object IsLoading : State()
        data class ShowFact(val fact: Fact) : State()
        data class ShowError(val error: Exception) : State()
    }

    private val _state = MutableStateFlow<State>(value = State.IsLoading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect {
                when (it) {
                    is Result.Success -> _state.value = State.ShowFact(it.data)
                    is Result.Error -> _state.value = State.ShowError(it.error)
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