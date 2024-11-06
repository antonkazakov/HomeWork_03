package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    private val _factFlow = MutableStateFlow<Result<Fact>>(Result.Loading)
    val factFlow: StateFlow<Result<Fact>> = _factFlow.asStateFlow()

    init {
        catsRepository.listenForCatFacts()
            .onEach { _factFlow.value = Result.Success(it) }
            .catch { _factFlow.value = Result.Error(it.message) }
            .launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}