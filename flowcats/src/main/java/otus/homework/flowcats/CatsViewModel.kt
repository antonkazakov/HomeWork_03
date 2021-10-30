package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.flowcats.network.model.FactResponse
import otus.homework.flowcats.repository.CatsRepository
import otus.homework.flowcats.repository.ICatRepository
import otus.homework.flowcats.ui.model.Fact
import otus.homework.flowcats.ui.model.Result

class CatsViewModel(
	private val catsRepository: ICatRepository
) : ViewModel() {

	private val _catsSharedFlow = MutableSharedFlow<Result<Fact>>()
	val catsSharedFlow: SharedFlow<Result<Fact>> = _catsSharedFlow


	init {
		getFacts()
	}


	fun getFacts() {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				_catsSharedFlow.emit(Result.InProgress())
				catsRepository.listenForCatFacts()
					.doOnError {
						_catsSharedFlow.emit(it)
						Log.d("лог","ошибка ${it.msg}")
					}
					.collect {
						Log.d("лог","факт - ${(it as Result.Success).data.text}")
						_catsSharedFlow.emit(it)
					}
			}
		}
	}


	private suspend fun <T> Flow<T>.doOnError(callback: suspend (error: Result.Error<T>) -> Unit): Flow<Result<T>> =
		flow {
			try {
				collect { value -> emit(Result.Success(value)) }
			} catch (t: Throwable) {
				callback(Result.Error(t.message ?: ""))
			}
		}

}

class CatsViewModelFactory(private val catsRepository: ICatRepository) :
	ViewModelProvider.NewInstanceFactory() {
	override fun <T : ViewModel> create(modelClass: Class<T>): T =
		CatsViewModel(catsRepository) as T
}