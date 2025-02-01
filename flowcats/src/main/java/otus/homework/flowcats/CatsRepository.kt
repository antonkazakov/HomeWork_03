package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

sealed class Result {
    class Success(val fact: Fact): Result()
    class Error(val exception: Throwable): Result()
}

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestFact = catsService.getCatFact()
                emit(Result.Success(latestFact))
            } catch (exception: Throwable) {
                emit(Result.Error(exception))
            }
            delay(refreshIntervalMs)
        }
    }
}
