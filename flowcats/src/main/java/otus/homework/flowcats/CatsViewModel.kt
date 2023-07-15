package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlowResult = MutableStateFlow<Result>(Result.Loading)
    val catsFlow = _catsFlowResult.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository
                .listenForCatFacts()
                .catch { _catsFlowResult.value = Result.Error(it)}
                .collect { newFact ->
                    _catsFlowResult.value = Result.Success(newFact)
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
