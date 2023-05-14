package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result?>(null)
    val catsStateFlow: StateFlow<Result?> = _catsStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    val successResult = Result.Success(it)
                    _catsStateFlow.value = successResult
                }
            } catch (e: Exception) {
                val exceptionResult = Result.Error(e.message)
                _catsStateFlow.value = exceptionResult
            }
        }
    }
}