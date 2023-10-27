package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<CatsEvent>()
    val events = _events.asSharedFlow()
    private val _state = MutableStateFlow(CatsState())
    val state = _state.asStateFlow()

    init {
        catsRepository
            .listenForCatFacts()
            .flowOn(Dispatchers.IO)
            .map(::mapping)
            .onEach(_state::emit)
            .catch {
                _events.emit(CatsEvent.Error(it.message ?: "Непредвиденная ошибка"))
                throw it
            }
            .retry()
            .launchIn(viewModelScope)
    }

    private fun mapping(fact: Fact): CatsState =
        CatsState(fact)
}

data class CatsState(
    val fact: Fact? = null
)

sealed interface CatsEvent {
    data class Error(val cause: String) : CatsEvent
}

class CatsViewModelFactory(
    private val catsRepository: CatsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = CatsViewModel(catsRepository) as T
}