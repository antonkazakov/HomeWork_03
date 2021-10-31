package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) : ViewModel() {

    private val _catImageState = MutableStateFlow<Result<String>>(Result.Success(EMPTY_STRING))
    val catImageState: StateFlow<Result<String>> = _catImageState

    init {
        viewModelScope.launch {
            withContext(coroutineDispatchers.ioDispatcher) {
                catsRepository.listenForCatImages()
                    .collect { _catImageState.value = it }
            }
        }
    }

    private companion object {
        const val EMPTY_STRING = ""
    }
}

class CatsViewModelFactory(
    private val catsRepository: CatsRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository, coroutineDispatchers) as T
}