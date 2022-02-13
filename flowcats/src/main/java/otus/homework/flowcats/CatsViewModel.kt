package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.Result.*

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Fact?>(null)
    val catsLiveData: StateFlow<Fact?> = _catsStateFlow

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    init {
        viewModelScope.launch {
                catsRepository.listenForCatFacts().collect { result ->
                    when(result){
                        is Success -> {
                            _catsStateFlow.emit(result.data)
                        }
                        is Error -> {
                            _toastMessage.emit(result.message)
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