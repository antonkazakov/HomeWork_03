package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CatsViewModel : ViewModel() {
    private val diContainer = DiContainer()
    private val _catsData = MutableStateFlow<Result>(Initial)
    val catsData = _catsData.asStateFlow()
    private var catsRepository: CatsRepository? = null

    init {
        catsRepository = diContainer.repository
        viewModelScope.launch {
            catsRepository?.listenForCatFacts()
                ?.onEach { fact ->
                    _catsData.emit(Success(fact = fact))
                }
                ?.catch { error ->
                    _catsData.emit(Error(throwable = error))
                }
                ?.launchIn(viewModelScope) ?: _catsData.emit(Initial)
        }
    }
}