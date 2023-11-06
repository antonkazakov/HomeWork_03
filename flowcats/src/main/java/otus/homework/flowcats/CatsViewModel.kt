package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result<Fact>?>(null)
    val catsStateFlow = _catsStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            catsRepository.listenForCatFacts()
                .onEach {
                    if (_catsStateFlow.value is Result.Error)
                        _catsStateFlow.value = null
                }
                .catch { e ->
                    _catsStateFlow.value = Result.Error(e.message ?: "Unexpected error")
                }
                .collect { _catsStateFlow.value = Result.Success(it) }
        }
    }

    companion object {
        val factory: (CatsRepository) -> ViewModelProvider.Factory = { catsRepository ->
            viewModelFactory {
                initializer {
                    CatsViewModel(catsRepository)
                }
            }
        }
    }
}