package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import otus.homework.flowcats.Result.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    // use shared flow to prevent using state flow default value , shared flow is close to live data
    private val _catsFlow =
        MutableSharedFlow<Fact>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val catsFLow: SharedFlow<Fact> = _catsFlow.asSharedFlow()

    private val _errorShow = MutableSharedFlow<String>()
    val errorShow: SharedFlow<String> = _errorShow

    init {

        catsRepository.listenForCatFacts().onEach {
            when (val data = it) {
                Nothing -> Unit
                is Error -> {
                    _errorShow.emit("some error")
                }

                is Success -> {
                    _catsFlow.emit(data.fact)
                }
            }
        }.launchIn(viewModelScope)

    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}