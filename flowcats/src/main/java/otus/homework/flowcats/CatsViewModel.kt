package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Result>(Result.Empty)
    val state: StateFlow<Result> = _state

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    catsRepository.listenForCatFacts().collect {
                        _state.value = Result.Success(fact = it)
                    }
                } catch (e: Exception) {
                    _state.value = Result.Error(e.message)
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