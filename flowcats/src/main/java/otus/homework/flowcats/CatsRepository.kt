package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import otus.homework.coroutines.Result

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        try {
            while (true) {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews[0]))
                delay(refreshIntervalMs)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}