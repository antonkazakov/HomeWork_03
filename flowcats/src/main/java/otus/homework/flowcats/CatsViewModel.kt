package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.data.Result
import otus.homework.flowcats.data.Success

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsLiveData = MutableStateFlow<Result>(Success(Fact.createMock()))
    val catsLiveData = _catsLiveData.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect {
                    _catsLiveData.value = it// Bug fix via by Dispatchers.Main
                }
            } catch(throwable:Throwable){
                Log.d("CatsViewModel","init -> $throwable ")
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatsViewModel(catsRepository) as T
    }
}