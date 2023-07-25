package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import otus.homework.flowcats.Result.Error
import otus.homework.flowcats.Result.Success

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFact = MutableStateFlow<Result?>(null)
    val catsFact = _catsFact.asStateFlow()

    init {
        catsRepository.listenForCatFacts()
            .onEach { fact -> _catsFact.emit(Success(catsModel = fact)) }
            .catch { exception -> _catsFact.emit(Error(error = exception)) }
            .launchIn(viewModelScope)
    }
}
