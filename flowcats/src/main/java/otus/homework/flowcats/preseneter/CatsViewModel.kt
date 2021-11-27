package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.data.CatsRepository
import otus.homework.flowcats.data.Fact

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateData: MutableStateFlow<Result<Fact>> = MutableStateFlow(Result.Loading)
    val catsStateData: StateFlow<Result<Fact>> = _catsStateData

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts()
                    .catch { ex ->  _catsStateData.value = Result.Error(ex as Exception) }
                    .collect {
                        _catsStateData.value = Result.Loading
                        delay(5000)
                        _catsStateData.value = Result.Success(it)
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