package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    private val _stateCats = MutableStateFlow(CatsFact(""))
    val stateCats: StateFlow<CatsFact> = _stateCats

    private val _stateError = MutableStateFlow<String?>(null)
    val stateError: StateFlow<String?> = _stateError

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts().collect(::handleResult)
            }
        }
    }

    private fun handleResult(result: Result<Fact>) =
        when (result) {
            is Result.Success ->
                if (result.data != null) {
                    _stateCats.value = CatsFact(result.data.text)
                } else {
                    _stateError.value = "Факт отсутствует или не найден"
                }
            is Result.Error ->
                _stateError.value = result.exceptionMessage ?: "Неопределенная ошибка"
        }
}
