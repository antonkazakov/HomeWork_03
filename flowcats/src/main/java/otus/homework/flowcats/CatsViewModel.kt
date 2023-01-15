package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
* Сейчас приложение крашится. Поправьте ошибку
* Крашится при попытке установить значение LiveData. т.к. LiveData обновляет UI, то установка значений происходит в MainThread,
* т.к. корутина запущена в IO потоке, происходит крэш.
* Фиксится установкой значения через liveData.postValue(), вызовом установки значения в новой корутине в Main потоке, или переключением контекста (но вот так делать не стоит)
* */

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Fact?>(null)
    val catsFlow = _catsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts().collect {
                    _catsFlow.emit(it)
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