package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val emptyFact = Fact("", false, "", "There is no message received ", "", "", "")
    private val _catsInputFlow = MutableStateFlow(emptyFact)
    val catsInputFlow: StateFlow<Fact> = _catsInputFlow
    private val _catsOutputFlow = MutableStateFlow(emptyFact)
    val catsOutputFlow = _catsOutputFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                catsRepository.listenForCatFacts().collect {
                    _catsInputFlow.value = it
                    _catsOutputFlow.value = catsInputFlow.value
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