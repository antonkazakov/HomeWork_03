package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class CatsViewModel(
    catsRepository: CatsRepository,
    initialState: CatsState = CatsState.default()
) : ViewModel() {

    val catsState get() = mState.asStateFlow()
    val events get() = mEvents.asSharedFlow()

    private val mState = MutableStateFlow(initialState)
    private val mEvents = MutableSharedFlow<CatsEvent>(extraBufferCapacity = 1)

    init {
        catsRepository.listenForCatFacts()
            .onEach(::handleCatsFactResult)
            .launchIn(viewModelScope)
    }

    private fun handleCatsFactResult(result: Result<Fact>) {
        when (result) {
            is Result.Success -> mState.modify {
                copy(fact = result.data)
            }
            is Result.Error -> mEvents.event { CatsEvent.ShowError(result.type) }
        }
    }

    private inline fun <T> MutableStateFlow<T>.modify(modifier: T.() -> T) {
        this.value = value.modifier()
    }

    private inline fun <T> MutableSharedFlow<T>.event(provider: () -> T) {
        tryEmit(provider())
    }

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}