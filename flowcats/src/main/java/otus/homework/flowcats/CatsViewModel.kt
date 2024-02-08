package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

//    private val _catsLiveData = MutableLiveData<Fact>()
//    val catsLiveData: LiveData<Fact> = _catsLiveData

    private val _catsStateFlow: MutableStateFlow<Result<Fact>> =
        MutableStateFlow(Result.Success(emptyFact()))
    val catsStateFlow = _catsStateFlow.asStateFlow()

    init {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                catsRepository.listenForCatFacts().collect {
//                    _catsLiveData.postValue(it)
//                }
//            }
//        }

        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch {
                    _catsStateFlow.value = Result.Error(it)
                }
                .collect { result ->
                    _catsStateFlow.value = Result.Success(result)
                }
        }
    }

    companion object {
        fun getCatsViewModelFactory(catsRepository: CatsRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    CatsViewModel(
                        catsRepository
                    )
                }
            }
    }
}