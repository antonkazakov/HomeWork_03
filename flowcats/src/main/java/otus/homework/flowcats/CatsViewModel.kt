package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow<Result>(InitialValue)
    val catsState: StateFlow<Result> = _catsState

    init {
        viewModelScope.launch {
                catsRepository.listenForCatFacts()
                    .catch {
                        _catsState.value = Error (it) }
                    .collect {
                    _catsState.value = Success(it)
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}