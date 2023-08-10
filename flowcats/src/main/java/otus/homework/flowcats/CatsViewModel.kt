package otus.homework.flowcats


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {


    private val _state = MutableStateFlow<Result?>(null)
    val state = _state.asStateFlow()

    init {
        getResult()
    }

    private fun getResult() {

        catsRepository.listenForCatFacts()
            .onEach { _state.value = it }
            .launchIn(viewModelScope)

    }
}

@Suppress("UNCHECKED_CAST")
class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}


