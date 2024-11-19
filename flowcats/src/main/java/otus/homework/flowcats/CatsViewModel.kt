package otus.homework.flowcats

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    companion object {

        val CATS_REPO = object : CreationExtras.Key<CatsRepository> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val catsRepository = this[CATS_REPO] as CatsRepository
                CatsViewModel(
                    catsRepository = catsRepository
                )
            }
        }
    }

    private val _catsFlow = MutableStateFlow<Fact?>(null)
    val catsFlow: StateFlow<Fact?> = _catsFlow

    private val _eventErrorMessage = MutableSharedFlow<String?>()
    val eventErrorMessage = _eventErrorMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> _eventErrorMessage.emit(result.error?.string())
                        is NetworkResult.Exception -> _eventErrorMessage.emit(result.e.message)
                        is NetworkResult.Success -> _catsFlow.value = result.data
                    }
                }
        }
    }
}