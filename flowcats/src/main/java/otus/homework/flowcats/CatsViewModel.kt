package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.handler.Result

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsDataFlow = MutableStateFlow<Result<*>>(Result.Success(Fact()))
    val catsDataFlow: StateFlow<Result<*>> = _catsDataFlow

    init {
        startListening()
    }

    fun startListening() {
        Log.d("CatsTag", "startListening")
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                Log.d("CatsTag", "launch coroutine")
                catsRepository.listenForCatFacts()
                    .collect {
                        Log.d("CatsTag", "Success")
                        _catsDataFlow.value = it
                    }
            }
        }
    }

    fun stopListening() {
        Log.d("CatsTag", "stopListening")
        viewModelScope.coroutineContext.cancelChildren()
    }

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
