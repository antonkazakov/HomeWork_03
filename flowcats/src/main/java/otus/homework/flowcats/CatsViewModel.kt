package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
  private val catsRepository: CatsRepository
) : ViewModel() {

  private val _catsState = MutableStateFlow<Result<Fact>>(Success.EMPTY)
  val catsState: StateFlow<Result<Fact>> = _catsState

  init {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        catsRepository.listenForCatFacts()
          .catch { exception -> _catsState.value = Error(exception.message ?: "Flow Error") }
          .collect { fact ->
            _catsState.value = Success(fact)
          }
      }
    }
  }
}

class CatsViewModelFactory(
  private val catsRepository: CatsRepository
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return CatsViewModel(catsRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}