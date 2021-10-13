package otus.homework.flowcats.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.data.CatsRepository
import otus.homework.flowcats.utils.Result

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catViewState: MutableStateFlow<CatViewState> =
        MutableStateFlow(CatViewState.CatFact())
    val catViewState: StateFlow<CatViewState>
        get() = _catViewState


    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _catViewState.value = CatViewState.CatFact(result.data)
                    }
                    is Result.Error -> {
                        _catViewState.value = CatViewState.CatError(result.errorMessage)
                    }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}