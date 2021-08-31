package otus.homework.flowcats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.model.Fact
import otus.homework.flowcats.utils.CatsRepository
import otus.homework.flowcats.utils.Result

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result<Fact>>(Result.Empty)
    val catsFlow: StateFlow<Result<Fact>> = _catsFlow

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _catsFlow.value = Result.Success(it)
                }
            } catch (e: Exception) {
                _catsFlow.emit(Result.Error(e))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}