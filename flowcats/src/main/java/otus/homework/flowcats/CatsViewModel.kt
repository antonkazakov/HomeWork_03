package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result?>(null)
    val catsFlow: Flow<Result?> = _catsFlow

    init {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                _catsFlow.tryEmit(Result.Error(throwable))
            }
        ) {
                catsRepository.listenForCatFacts().collect {
                    _catsFlow.value = Result.Success(it)
                }
            }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}