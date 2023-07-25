package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<MainState<Fact?>>(MainState.Success(null))
    val catsStateFlowData = _catsStateFlow.asStateFlow()

    init {
        catsRepository.listenForCatFacts()
            .onEach { fact -> _catsStateFlow.value = MainState.Success(fact) }
            .catch { _catsStateFlow.value = MainState.Error }
            .launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}