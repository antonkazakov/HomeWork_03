package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow("Loading...")
    val catsFlow: StateFlow<String> = _catsFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts()
                    .collect {
                        when(it) {
                            is Result.Success -> _catsFlow.emit(it.value.text)
                            is Result.Error -> _catsFlow.emit("Oops! Error =(")
                        }
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