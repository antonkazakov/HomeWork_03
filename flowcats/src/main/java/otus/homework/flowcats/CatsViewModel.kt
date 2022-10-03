package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result<Any>>(Result.Loading)
    val catsStateFlow: StateFlow<Result<Any>> = _catsStateFlow

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().flowOn(Dispatchers.IO).collect {
                when (it) {
                    is Fact -> _catsStateFlow.value = Result.Success(it)
                    is Exception -> _catsStateFlow.value = Result.Error(it)
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