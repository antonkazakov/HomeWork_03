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

    private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData

    private val _catsFlow = MutableStateFlow<Result<Fact>>(Result.Empty())
    val catsFlow: StateFlow<Result<Fact>> = _catsFlow






    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts()
                    .catch { _catsFlow.value = Result.Error(it.message) }
                    .collect {
                    _catsFlow.value = it
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


sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val message: String?) : Result<T>()
    class Empty<T>: Result<T>()
}