package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFactFlow = MutableStateFlow<Result?>(null)
    val catsFactFlow: StateFlow<Result?> = _catsFactFlow
    init{
        viewModelScope.launch {
           catsRepository.listenForCatFacts().collect {facts ->
                _catsFactFlow.value = facts
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}