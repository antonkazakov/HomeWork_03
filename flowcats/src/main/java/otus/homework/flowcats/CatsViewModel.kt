package otus.homework.flowcats

import androidx.lifecycle.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Result>(Error("No fact"))
    val state = _state.asStateFlow()



    init {
        catsRepository.listenForCatFacts().onEach {
            _state.value = Success(it)
        }.catch {
            _state.value = Error("Fact error")
        }.launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}