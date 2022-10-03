package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlowData = MutableStateFlow<Result>(Result.Success<Fact>(null))
    val catsStateFlowData: StateFlow<Result> = _catsStateFlowData

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { exception ->
                    val errorResult = Result.Error(exception.message ?: "Unknown error")
                    _catsStateFlowData.value = errorResult
                }
                .collect { fact ->
                    val successResult = Result.Success(fact)
                    _catsStateFlowData.value = successResult
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}