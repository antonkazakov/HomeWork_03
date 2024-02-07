package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _dataState = MutableStateFlow<Result<Fact>>(Result.Loading)
    val dataState: StateFlow<Result<Fact>> = _dataState

    init {
        catsRepository
            .listenForCatFacts()
            .onEach {
                _dataState.value = Result.Success(it)
            }
            .catch {
                _dataState.value = Result.Error(it)
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