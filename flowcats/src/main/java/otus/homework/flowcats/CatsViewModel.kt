package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow: MutableStateFlow<Fact?> = MutableStateFlow(null)
    val catsFlow: StateFlow<Fact?> = _catsFlow

    init {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d("init", "Exception = '$exception'")
        }
        viewModelScope.launch(handler) {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect { result ->
                    when (result) {
                        is Result.Success -> _catsFlow.value = result.fact
                        is Result.Error -> _catsFlow.value = null
                    }
                }
            }
        }
    }

    companion object {
        val REPOSITORY_KEY = object : CreationExtras.Key<CatsRepository> {  }
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = this[REPOSITORY_KEY] as CatsRepository
                CatsViewModel(repository)
            }
        }
    }
}
