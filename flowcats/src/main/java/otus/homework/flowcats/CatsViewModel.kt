package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow: MutableStateFlow<Result<Any?>> = MutableStateFlow(Result.Empty)
    val catsStateFlow: StateFlow<Result<Any?>> = _catsStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect {
                when(it){
                    is Result.Succsess ->  _catsStateFlow.value = Result.Succsess(it.fact)
                    is Result.Error ->  _catsStateFlow.value = Result.Error(it.e)
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