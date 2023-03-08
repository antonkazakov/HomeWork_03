package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsStateFlowViewModel(
    private val catsRepository: CatsRepository,
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result>(Result.Empty)
    val catsStateFlow: StateFlow<Result> = _catsStateFlow

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    catsRepository.listenForCatFacts().collect {
                        _catsStateFlow.value = Result.Success(it)
                    }
                } catch (e: Exception) {
                    _catsStateFlow.value = Result.Error(e.message.toString())
                }
            }
        }
    }
}

class CatsStateFLowViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsStateFlowViewModel(catsRepository) as T
}