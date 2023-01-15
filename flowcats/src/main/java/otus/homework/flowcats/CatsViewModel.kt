package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.Result

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {
    val catsState = catsRepository.listenForCatFacts()
        .flowOn(Dispatchers.IO)
        .map { Result.Success(it) }
        .catch<Result<Fact>> {
            emit(Result.Error(it))
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Result.Loading
        )
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}