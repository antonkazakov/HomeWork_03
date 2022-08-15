package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result>(Success(Loading))
    var catsStateFlow: StateFlow<Result> = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository
                .listenForCatFacts()
                .map { fact -> Success(Signature(fact.text)) as Result }
                .catch { throwable: Throwable -> emit(Error(throwable)) }
                .onEach { result -> _catsStateFlow.emit(result) }
                .collect()
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}