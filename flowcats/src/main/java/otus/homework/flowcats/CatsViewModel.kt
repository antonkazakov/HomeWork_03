package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _cats = MutableStateFlow<Fact?>(value = null)
    val cats: StateFlow<Fact?> = _cats

    private val _error = MutableStateFlow<Exception?>(value = null)
    val error: StateFlow<Exception?> = _error

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect {
                when (it) {
                    is Result.Success -> _cats.value = it.data
                    is Result.Error -> _error.value = it.error
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