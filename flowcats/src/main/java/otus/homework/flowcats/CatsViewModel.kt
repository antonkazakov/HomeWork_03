package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow<Result>(Result.Success(emptyFact()))
    val catsState: StateFlow<Result> = _catsState.asStateFlow()

    private fun emptyFact() = Fact(
        "", false, "", "Facts nod loaded yet", "", false, "", "", ""
    )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository
                    .listenForCatFacts()
                    .catch { _catsState.value = Result.Error(it) }
                    .collect { _catsState.value = Result.Success(it) }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
