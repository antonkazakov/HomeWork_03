package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlowData = MutableSharedFlow<Result>()
    val catsFlowData: SharedFlow<Result> = _catsFlowData

//    private val _catsLiveData = MutableLiveData<Fact>()
//    val catsLiveData: LiveData<Fact> = _catsLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
//                catsRepository.listenForCatFacts().collect {
//                    withContext(Dispatchers.Main) { // fix
//                        _catsLiveData.value = it
//                    }
//                }
            catsRepository.listenForCatFacts().collect {
                _catsFlowData.emit(it)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}
