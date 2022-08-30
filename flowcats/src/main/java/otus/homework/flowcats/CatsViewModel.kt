package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    val catsStateFlow: StateFlow<Result<Fact?>> = catsRepository.listenForCatFacts()
        .catch {
            flowOf(Result.Error(it.message, it))
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, Result.Loading(null))

}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}