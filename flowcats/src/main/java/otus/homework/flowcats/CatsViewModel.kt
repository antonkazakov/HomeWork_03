package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    lateinit var catsFlow: StateFlow<Fact>

    init {
        viewModelScope.launch {
            catsFlow = catsRepository.listenForCatFacts()
                .stateIn(
                    scope = this,
                    started = SharingStarted.Eagerly,
                    initialValue = Fact.EMPTY
                )
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}