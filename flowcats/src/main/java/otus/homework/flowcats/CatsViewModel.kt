package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlowUi = MutableStateFlow<Result>(Result.Success())
    val catsFlowUi: StateFlow<Result> = _catsFlowUi

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect { fact ->
                val uiRes = if (fact.length.isNotEmpty()) Result.Success(fact) else Result.Error(fact.text)
                _catsFlowUi.value = uiRes
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}