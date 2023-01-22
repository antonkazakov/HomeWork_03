package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val emptyFact = Fact("", false, "", "There is no message received ", "", "", "")
    private val refreshIntervalMs: Long = 5000
    private val _catsInputFlow = MutableStateFlow(emptyFact)
    val catsInputFlow: StateFlow<Fact> = _catsInputFlow
    private val _catsOutputFlow = MutableStateFlow(emptyFact)
    val catsOutputFlow = _catsOutputFlow.asStateFlow()

    companion object {
        private const val TAG = "CatsViewModel"
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts().collect {
                    when (it) {
                        is Result.Success -> {
                            _catsInputFlow.value = it.result
                        }
                        is Result.Error -> {
                            CrashMonitor.trackWarning(TAG, it.errorMessage)
                            _catsInputFlow.value = emptyFact.copy(text = it.errorMessage) }
                    }
                    _catsOutputFlow.value = catsInputFlow.value
                    delay(refreshIntervalMs)
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}