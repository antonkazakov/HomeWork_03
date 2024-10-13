package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository,
    private val mapper: LoadResult.Mapper<ICatsUiState>
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<ICatsUiState>(ICatsUiState.Loading("Loading..."))
    val catsFlow = _catsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect { loadResult ->
                _catsFlow.emit(loadResult.map(mapper))
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository,
                           private val mapper: LoadResult.Mapper<ICatsUiState>) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == CatsViewModel::class.java) return CatsViewModel(catsRepository, mapper) as T
        throw IllegalArgumentException()
    }
}