package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<State>(State.Init)
    val stateFlow: StateFlow<State> = _stateFlow

    init {
        startListenForCatFacts()
    }

    private fun startListenForCatFacts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _stateFlow.value = State.Data(result.fact)
                        }
                        is Result.Error -> {
                            _stateFlow.value = State.Error(result.ex)
                        }
                    }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CatsViewModel::class.java ->
                CatsViewModel(catsRepository) as T

            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}