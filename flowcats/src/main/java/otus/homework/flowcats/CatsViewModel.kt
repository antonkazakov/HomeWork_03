package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.Result.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsMutableStateFlow = MutableStateFlow<Result>(Success(Fact()))
    val catsMutableStateFlow: StateFlow<Result> = _catsMutableStateFlow

    init {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    catsRepository.listenForCatFacts().collect {
                        _catsMutableStateFlow.value = Success(it)
                    }
                }
            } catch (e: Exception){
                _catsMutableStateFlow.value = Error(e)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}