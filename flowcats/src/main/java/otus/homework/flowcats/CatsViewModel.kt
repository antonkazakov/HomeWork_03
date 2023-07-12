package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result<Fact>>(Result.Success(createEmptyFact()))
    val catsFlow = _catsFlow.asStateFlow()

    private fun createEmptyFact(): Fact {
        return Fact(
            createdAt = "",
            deleted = false,
            id = "",
            text = "",
            source = "",
            used = false,
            type = "",
            user = "",
            updatedAt = ""
        )
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts()
                    .map { Result.Success(it) as Result<Fact> }
                    .catch { emit(Result.Error("Ошибка соединения с сервером :(")) }
                    .collect {
                    withContext(Dispatchers.Main) {
                        _catsFlow.value = it
                    }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
