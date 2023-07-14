package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.Result.Error
import otus.homework.flowcats.Result.Success

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFact = MutableStateFlow<Result>(
        Success(catsModel = Fact())
    )
    val catsFact = _catsFact.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _catsFact.emit(Error(error = exception))
        }
    }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts()
                    .catch { exception -> _catsFact.emit(Error(error = exception)) }
                    .collect { fact -> _catsFact.emit(Success(catsModel = fact)) }
            }
        }
    }
}
