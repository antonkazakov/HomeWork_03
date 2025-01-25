package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect {
                when (it) {
                    is Result.Success<*> -> (it.data as? Fact)?.let { fact ->
                        _state.emit(State.Success(fact.text))
                    }

                    is Result.Error -> _state.emit(State.Error(it.t.message ?: ""))
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}