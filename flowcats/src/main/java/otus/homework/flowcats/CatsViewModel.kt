package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    val catsFlow = catsRepository.listenForCatFacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}