package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsData = MutableStateFlow<Fact>(value = Unit as Fact)
    val catsData : StateFlow<Fact> = _catsData

    fun collectFacts() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    catsRepository.listenForCatFacts().collect {
                        _catsData.value = it
                    }
                }
            }
    }

    fun receiveFacts() : Flow<Result> {
        return flow {
            try {
                catsData.collectLatest() {
                    it?.also {
                        emit(Success<Fact>(it))
                    }
                }
            }
            catch (e: Exception) {
                emit(Error("Error: " + e.message))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result()

data class Error(val message : String) : Result()

data class Success<T> (val result : T) : Result()
