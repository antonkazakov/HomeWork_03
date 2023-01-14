package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow<Result>(Result.Empty)
    val catsStateFlow: StateFlow<Result> = _catsStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch {
                    emit(
                        Fact(
                            fact = "Stream Error",
                            length = 0
                        )
                    )
                }
                .collect {
                    _catsStateFlow.value = Result.Success(it)
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
