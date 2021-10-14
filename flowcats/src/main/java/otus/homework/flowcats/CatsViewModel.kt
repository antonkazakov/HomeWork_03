package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    sealed class State {
        object InProgress : State()
        data class Default(val fact: Fact) : State()
        data class Error(val message: String) : State()
    }

    val stateFlow = catsRepository.listenForCatFacts()
        .map { result ->
            when (result) {
                is Result.Success -> State.Default(result.data)
                is Result.Error -> State.Error(result.message)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, State.InProgress)
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}