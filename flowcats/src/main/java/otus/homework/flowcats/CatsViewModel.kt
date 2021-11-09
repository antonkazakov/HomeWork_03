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

  private val _catsStateFlow = MutableStateFlow<Result>(Empty)
  val catsStateFlow: StateFlow<Result> = _catsStateFlow

  init {
    viewModelScope.launch {
      catsRepository.listenForCatFacts()
        .catch {
          _catsStateFlow.emit(Error(it.message ?: "something nasty happened"))
        }
        .collect {
          _catsStateFlow.emit(Success(it))
        }
    }
  }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
  ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
    CatsViewModel(catsRepository) as T
}
