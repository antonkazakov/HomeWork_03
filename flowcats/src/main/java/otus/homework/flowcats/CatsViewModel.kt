package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result?>(null)
    val catsStateFlow: StateFlow<Result?> = _catsStateFlow

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _catsStateFlow.value = Result.Success(it)
                }
            } catch (e: Exception) {
                _catsStateFlow.value = Result.Error(e.message ?: "Error")
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}