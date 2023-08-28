package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableStateFlow<Result>(Loading)
    val catsLiveData = _catsLiveData.asStateFlow()

    init {
        viewModelScope.launch {
            while (true)
                try {
                    _catsLiveData.value = Success(catsRepository.getCatFact())
                    delay(5000)
                }
                catch (e: Exception) {
                    _catsLiveData.value = Error(e.toString())
                    break
                }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatsViewModel(catsRepository) as T
    }
}
