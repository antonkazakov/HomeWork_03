package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Error

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result<Fact>>(Result.Success(Fact("", false, "", "", "", false, "", "", "")))
    val catsFlow : StateFlow<Result<Fact>> = _catsFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    try {
                        _catsFlow.value = Result.Success(it)
                    } catch (e: Error) {
                        _catsFlow.value = Result.Error(e.message, e)
                    }
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