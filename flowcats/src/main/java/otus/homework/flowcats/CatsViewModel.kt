package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CancellationException
import otus.homework.flowcats.network.model.FactResponse
import otus.homework.flowcats.repository.CatsRepository
import otus.homework.flowcats.repository.ICatRepository
import otus.homework.flowcats.ui.model.Fact
import otus.homework.flowcats.ui.model.Result
import java.io.IOException

class CatsViewModel(
	private val catsRepository: ICatRepository
) : ViewModel() {

	private val _catsSharedFlow = MutableSharedFlow<Result<Fact>>()
	val catsSharedFlow: SharedFlow<Result<Fact>> = _catsSharedFlow

	private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
		Log.d("errors","CatsViewModel::CoroutineExceptionHandler ${throwable.message}")
	}

	init {
		getFacts()
	}


	fun getFacts() {
		viewModelScope.launch(handler) {
			_catsSharedFlow.emit(Result.InProgress())
			try{
				catsRepository.listenForCatFacts()
					.flowOn(Dispatchers.IO)
					.collect {
						_catsSharedFlow.emit(Result.Success<Fact>(it))
					}
			}catch(c:CancellationException){
				throw c
			}catch(io: IOException){
				_catsSharedFlow.emit(Result.Error(io.message?:""))
			}

		}
	}
}

class CatsViewModelFactory(private val catsRepository: ICatRepository) :
	ViewModelProvider.NewInstanceFactory() {
	override fun <T : ViewModel> create(modelClass: Class<T>): T =
		CatsViewModel(catsRepository) as T
}