package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.Result.Success
import otus.homework.flowcats.Result.Error

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Result?>(null)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .flowOn(Dispatchers.IO)
                .catch { _state.value = Error(it) }
                .collect { fact ->
                    withContext(Dispatchers.Main) {
                        _state.value = Success(fact)
                    }
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}