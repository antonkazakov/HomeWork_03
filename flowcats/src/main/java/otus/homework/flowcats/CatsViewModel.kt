package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Result?>(null)
    val uiState: StateFlow<Result?> = _uiState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository
                    .listenForCatFacts()
                    .catch { throwable ->
                        Log.e("CatsViewModel", "Exception: ${throwable.message}")
                        _uiState.emit(Error(throwable.message ?: throwable.toString()))
                    }
                    .collect {
                        _uiState.emit(Success(it))
                    }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
