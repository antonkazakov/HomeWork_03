package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow: MutableStateFlow<Result> = MutableStateFlow<Result>(Result.Empty)
    val catsFlow: StateFlow<Result> = _catsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .collect { result ->
                    /**
                     * Можно подтянуть более свежие зависимости корутин, и вызвать метод update,
                     * тогда операция обновления значения будет атомарной
                     * (в текущей версии зависимостей метода нет).
                     * В данном случае, операция осуществляется из Main, поэтому разницы нет.
                     * */
                    _catsFlow.value = result
                }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}