package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow<CatsState>(CatsState.Default)
    val catsState: StateFlow<CatsState> = _catsState.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { error ->
                    _catsState.value = CatsState.CatsError(error.message ?: "")
                }
                .collect { fact ->
                    _catsState.value = CatsState.CatsSuccess(fact)
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}