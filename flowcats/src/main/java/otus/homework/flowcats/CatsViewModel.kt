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

    private val _catsStateFlow: MutableStateFlow<Result> = MutableStateFlow<Result>(Initial)
    val catsStateFlow: StateFlow<Result> = _catsStateFlow


    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts()
                    .catch { throwable ->
                        _catsStateFlow.value = Error(throwable)

                    }
                    .collect { fact ->
                        _catsStateFlow.value = Success(fact)
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