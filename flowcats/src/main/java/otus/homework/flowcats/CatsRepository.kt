package otus.homework.flowcats

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (currentCoroutineContext().isActive) {
            try {
                val fact = catsService.getCatFact()
                emit(Result.Success(fact))
                delay(refreshIntervalMs)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }
}