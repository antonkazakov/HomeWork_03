package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {


    private val _cats = MutableStateFlow<Fact?>(null)
    val cats: StateFlow<Fact?> = _cats

    private val _error = MutableStateFlow<Exception?>(null)
    val error: StateFlow<Exception?> = _error

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts()
                    .flowOn(Dispatchers.IO)
                    .collect { _cats.value = it }

            } catch (e: Exception) {
                _error.value = e
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}