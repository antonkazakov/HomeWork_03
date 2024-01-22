package otus.homework.flowcats

import androidx.lifecycle.*
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

    private val _catsLiveData = MutableStateFlow<Result<Fact>>(Result.Idle)
    val catsLiveData: StateFlow<Result<Fact>> = _catsLiveData

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository
                    .listenForCatFacts()
                    .catch {
                        _catsLiveData.value = Result.Error(it)
                    }
                    .collect { fact ->
                        _catsLiveData.value = Result.Success(fact)
                    }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}