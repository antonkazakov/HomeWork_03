package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 1000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (t: Throwable) {
                emit(Result.Error)
            }
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}


sealed class Result {
    data class Success(val fact: Fact) : Result()
    object Error : Result()
    object EmptyResult : Result()
}