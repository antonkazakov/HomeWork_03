package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<Fact>()
    val catsLiveData: LiveData<Fact> = _catsLiveData

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                catsRepository.listenForCatFacts()
                    .collect(::emitCatsLiveDataValue)
            }
        }
    }

    private suspend fun emitCatsLiveDataValue(fact: Fact) {
        withContext(Dispatchers.Main) {
            _catsLiveData.value = fact
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}