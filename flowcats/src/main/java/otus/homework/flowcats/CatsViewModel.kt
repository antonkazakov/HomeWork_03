package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow<CatResult>(CatInit("loading..."))
    val catsState: StateFlow<CatResult> = _catsState

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().catch {
                _catsState.emit(CatError("Ошибка загрузки"))
            }.collect {
                _catsState.emit(CatSuccess(it))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class CatResult
data class CatError(var errorTxt: String) : CatResult()
data class CatSuccess(var fact: Fact) : CatResult()
data class CatInit(var txt: String) : CatResult()