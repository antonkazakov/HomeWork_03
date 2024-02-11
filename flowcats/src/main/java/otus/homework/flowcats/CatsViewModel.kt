package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onErrorCollect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result>(Result.Loading)
    val catsFlow = _catsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().catch {
                _catsFlow.value = Result.Error(it.message ?: "Unknown error")
            }.collect {
                _catsFlow.value = Result.Success(it)
            }
        }
    }

    sealed interface Result {
        data class Success(val fact: Fact) : Result
        data class Error(val error: String) : Result

        object Loading : Result
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}