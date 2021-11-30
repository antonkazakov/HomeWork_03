package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository,
) : ViewModel() {
    private val _catsStateFlow = MutableStateFlow<Result<Fact>>(Result.Loading())
    val catsStateFlow: StateFlow<Result<Fact>> get() = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().catch { throwable ->
                _catsStateFlow.emit(Result.Error(throwable.localizedMessage ?: "Unknown"))
            }.collect { fact ->
                _catsStateFlow.emit(Result.Success(fact))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}