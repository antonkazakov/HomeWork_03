package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result<Fact, String>>(Result.Idle)
    val catsFlow: StateFlow<Result<Fact, String>> = _catsFlow

    init {
        catsRepository
            .listenForCatFacts()
            .map {
                Result.Success(it) as Result<Fact, String>
            }
            .catch { t ->
                t.message?.let { emit(Result.Error(it)) }
            }
            .onEach {
                _catsFlow.emit(it)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}