package otus.homework.flowcats

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}