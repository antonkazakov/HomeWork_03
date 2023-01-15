package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result>(Result.Initial)
    val catsStateFlow: StateFlow<Result> = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .filter { it != Fact.INITIAL }
                .onEach { _catsStateFlow.value = Result.Success(it) }
                .flowOn(Dispatchers.IO)
                .catch {
                    _catsStateFlow.value = Result.Error("$it")
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}