package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow(Fact.INITIAL)
    val catsStateFlow: StateFlow<Fact> = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .filter { it != Fact.INITIAL }
                .onEach { _catsStateFlow.value = it }
                .flowOn(Dispatchers.IO)
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}