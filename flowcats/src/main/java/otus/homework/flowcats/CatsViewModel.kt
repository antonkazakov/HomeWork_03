package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    val catsFlow = catsRepository.listenForCatFacts()
        .map{ fact -> fact?.let{Success(it)} ?: Error }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result
object Loading: Result()
object Error: Result()
data class Success(val fact: Fact) : Result()