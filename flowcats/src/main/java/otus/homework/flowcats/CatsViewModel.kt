package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import otus.homework.flowcats.CatsViewModel.Result.*

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    sealed class Result {
        data class Success(val fact: Fact) : Result()
        data class Error(val message: String) : Result()
        object Loading : Result()
    }

    private val _catFacts = MutableStateFlow<Result>(Loading)
    val catFacts: StateFlow<Result> = _catFacts

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { _catFacts.emit(Error(it.message ?: "No error message")) }
                .collect { _catFacts.emit(Success(it)) }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
