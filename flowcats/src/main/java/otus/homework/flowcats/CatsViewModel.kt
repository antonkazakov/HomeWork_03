package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result>(Result.Loading)
    val catsFlow: StateFlow<Result>
        get() = _catsFlow.asStateFlow()

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->

            Log.e(ERROR_TAG, "$error.message")
            _catsFlow.value = Result.Error(error.message.toString())

        }) {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {fact ->
                    _catsFlow.value = Result.Success(fact)
                }
            }
        }
    }

    private companion object{
        const val ERROR_TAG = "CATS_ERROR"
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}