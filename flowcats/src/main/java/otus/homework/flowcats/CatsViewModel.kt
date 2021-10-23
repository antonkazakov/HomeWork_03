package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlowData = MutableStateFlow<Result>(Result.InitValue)
    val catsFlowData: StateFlow<Result> = _catsFlowData

    init {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    catsRepository.listenForCatFacts()
                        .collect { _catsFlowData.emit(Result.Success(it)) }
                }
            } catch (e: Throwable) {
                _catsFlowData.emit(Result.Error(e.message.toString()))
            }
        }
    }

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}