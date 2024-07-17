package otus.homework.flowcats

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var errorCount = 0

    fun listenForCatFacts() = flow {
        while (true) {
            val result = try {
                Result.Success<Fact>(body = catsService.getCatFact())
            } catch (e: Throwable) {
                Result.Error
            }
            emit(result)

            if (result == Result.Error) {
                errorCount++
                val errorDelay = errorCount * refreshIntervalMs
                delay(errorDelay)
            } else {
                errorCount = 0
                delay(refreshIntervalMs)
            }
        }
    }.flowOn(ioDispatcher)
}