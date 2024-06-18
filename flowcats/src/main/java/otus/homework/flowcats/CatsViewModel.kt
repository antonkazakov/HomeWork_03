package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.model.Fact
import otus.homework.flowcats.model.Result


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catFacts: MutableStateFlow<Result?> =  MutableStateFlow(null)
    val catFacts = _catFacts.asStateFlow()


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    _catFacts.value = it
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T & Any {
        return CatsViewModel(catsRepository) as (T & Any)
    }
}