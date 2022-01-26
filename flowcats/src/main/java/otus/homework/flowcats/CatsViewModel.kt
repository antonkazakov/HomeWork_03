package otus.homework.flowcats

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import otus.homework.coroutines.IResourceProvider

class CatsViewModel(
    private val catsRepository: CatsRepository,
    private val resources: IResourceProvider
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result<Fact>>(Result.Loading)
    val catsFlow: StateFlow<Result<Fact>> = _catsFlow

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { _catsFlow.value = Result.Error(resources.getString(R.string.exception_message)) }
                .collect { _catsFlow.value = Result.Success(it) }
        }
    }
}

class CatsViewModelFactory(
    private val catsRepository: CatsRepository,
    private val context: Context
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository, DiContainer.getResources(context)) as T
}