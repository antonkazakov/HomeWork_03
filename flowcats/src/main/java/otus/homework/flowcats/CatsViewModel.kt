package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result?>(null)
    val catsFlow: Flow<Result?> = _catsFlow

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _catsFlow.value = Result.Success(it)
                }
            } catch (e: Exception) {
                _catsFlow.tryEmit(Result.Error(e))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}