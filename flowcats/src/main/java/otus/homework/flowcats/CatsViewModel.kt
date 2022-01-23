package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository,
) : ViewModel() {
    private val defaultFact = Fact("Your cat will never criticize you for your night meals. He will take a meal with you.")

    private val _catsFactsFlow = MutableStateFlow<Result<Fact>>(Result.Success(defaultFact))
    val catsFactsFlow = _catsFactsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { _catsFactsFlow.value = Result.Error(it.message) }
                .collect { _catsFactsFlow.value = Result.Success(it) }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CatsViewModel(catsRepository) as T
}