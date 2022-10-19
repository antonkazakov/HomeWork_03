package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val initialUiState = Result.Success(
        Fact(
            createdAt = "",
            deleted = false,
            id = "",
            text = "",
            source = "",
            used= false,
            type = "",
            user = "",
            updatedAt = ""
        )
    )

    private val _catsUiState = MutableStateFlow<Result>(initialUiState)
    val catsUiState = _catsUiState.asStateFlow()


    init {
        viewModelScope.launch {

            catsRepository.listenForCatFacts()
                .retry {
                    _catsUiState.emit(Result.Error(it as Exception))
                    true
                }
                .collect {
                    _catsUiState.emit(Result.Success(it))
                    Log.d("myLog", "collect code")
                }

        }
    }


}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}