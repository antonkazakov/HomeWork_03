package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsViewState = MutableStateFlow<Result>(Result.Loading)
    val catsFlow = _catsViewState.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .map {
                    Result.Success(it)
                }
                .catch {
                    _catsViewState.value = Result.Error(it.message.toString())
                }
                .collect { result ->
                    _catsViewState.value = result
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}