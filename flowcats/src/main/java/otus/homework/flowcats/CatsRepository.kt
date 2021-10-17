package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Success(latestNews))
            delay(refreshIntervalMs)
        }
    }.catch {
        emit(Error)
    }.flowOn(Dispatchers.IO)
}

sealed class Result
object Loading: Result()
object Error: Result()
data class Success(val fact: Fact) : Result()