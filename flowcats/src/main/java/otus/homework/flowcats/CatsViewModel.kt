package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import otus.homework.flowcats.model.Result

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result<*>?>(null)
    val catsStateFlow = _catsStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            toReceive()
        }
    }

    private suspend fun toReceive() {
        catsRepository
            .listenForCatFacts()
            .onEach {
                _catsStateFlow.tryEmit(Result.Success(it))
            }
            .catch {
                _catsStateFlow.tryEmit(Result.Error(it))
                toReceive()
            }
            .collect()
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}