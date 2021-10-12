package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlowData = MutableStateFlow<Result<Fact>>(Result.Empty("Fetching..."))
    val catsFlowData: StateFlow<Result<Fact>> = _catsFlowData

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { e ->
                    _catsFlowData.emit(
                        Result.Error(
                            e.message ?: "Error occurred while fetching data!!!"
                        )
                    )
                }.collect {
                    _catsFlowData.emit(Result.Success(it))
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}