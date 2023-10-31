package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow(
        Fact(
            createdAt = "",
            deleted = false,
            id = "",
            text = "",
            source = "",
            used = false,
            type = "",
            user = "",
            updatedAt = "",
        )
    )
    val catsState = _catsState as StateFlow<Fact>

    init {
        catsRepository.listenForCatFacts().onEach {
            _catsState.value = it
        }
            .launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
