package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val result = wrapRequest { catsService.getCatFact() }
            emit(result)
            delay(refreshIntervalMs)
        }
    }

    private suspend fun <T> wrapRequest(block: suspend () -> T): Result<T> =
        try {
            val result = block()
            Result.Success(result)
        } catch (e: Exception) {
            Result.unknownError()
        }

}