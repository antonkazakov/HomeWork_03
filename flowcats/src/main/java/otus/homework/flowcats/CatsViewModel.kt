package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableStateFlow<Fact>(Fact("", 0))
    val catsLiveData: StateFlow<Fact> = _catsLiveData.asStateFlow()

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    _catsLiveData.value = if (it is Result) Fact("Error: " + SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale("RU")).format(Date()), 0) else it as Fact
                }
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
