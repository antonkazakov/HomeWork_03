package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow(Resp.Success(Fact()))
    val catsState: StateFlow<Resp.Success> = _catsState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    when (it) {
                        is Resp.Success -> {
                            _catsState.value = it
                        }
                        is Resp.Error -> {
                            _catsState.value = Resp.Success(Fact(it.exception.toString()))
                        }
                    }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Resp {
    data class Success(val fact: Fact) : Resp()
    data class Error(val exception: Throwable) : Resp()
}