package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    private val _cats = MutableStateFlow<Result<Fact>?>(null)
    val cats = _cats.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _cats.emit(Result.Success(it))
                }
            } catch (e: Exception) {
                _cats.emit(Result.Error("Error: $e"))
            }
        }
    }
}

class CatsViewModelFactory(
    private val catsRepository: CatsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}