package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = try {
                Result.Success(catsService.getCatFact())
            } catch (ex: Throwable) {
                Result.Error(ex.message, ex)
            }
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
}