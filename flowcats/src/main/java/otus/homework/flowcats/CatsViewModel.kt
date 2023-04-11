package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result<Fact>?>(null)
    val catsFlow: StateFlow<Result<Fact>?> = _catsFlow

    init {
        catsRepository.listenForCatFacts()
            .map<Fact, Result<Fact>> { Result.Success(it) }
            .catch { emit(Result.Error(it)) }
            .onEach { _catsFlow.value = it }
            .launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}