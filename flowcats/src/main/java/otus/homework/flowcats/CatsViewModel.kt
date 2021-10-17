package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {


        private val _catsFlow = MutableStateFlow<ResultCats<Fact>>(ResultCats.Empty)
        val catsFlow: StateFlow<ResultCats<Fact>> = _catsFlow

    init {
        viewModelScope.launch {
                catsRepository.listenForCatFacts()
                    .flowOn(Dispatchers.IO)
                    .collect {
                         _catsFlow.value = it
                        }
                }
        }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}



