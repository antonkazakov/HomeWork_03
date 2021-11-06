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


    private val _state = MutableStateFlow<CatResultModel?>(null)
    val state: StateFlow<CatResultModel?> = _state

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts()
                    .flowOn(Dispatchers.IO)
                    .collect { _state.value = CatResultModel.Success(it) }

            } catch (e: Exception) {
                _state.value = CatResultModel.Error(e)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}