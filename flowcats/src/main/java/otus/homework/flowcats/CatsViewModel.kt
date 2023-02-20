package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {


    private val _catsInputFlow = MutableStateFlow(EMPTY_FACT)
    val catsInputFlow: StateFlow<Fact> = _catsInputFlow

    companion object {
        private const val TAG = "CatsViewModel"
        private val EMPTY_FACT = Fact("", false, "",
                        "There is no message received ", "", "", "")
        private val REFRESH_INTERVAL_MS: Long = 5000
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    when (it) {
                        is Result.Success -> {
                            _catsInputFlow.value = it.result
                        }
                        is Result.Error -> {
                            CrashMonitor.trackWarning(TAG, it.errorMessage)
                            _catsInputFlow.value = EMPTY_FACT.copy(text = it.errorMessage) }
                    }
                    delay(REFRESH_INTERVAL_MS)
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