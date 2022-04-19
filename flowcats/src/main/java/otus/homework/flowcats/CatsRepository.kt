package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 1000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()

            if (Random.nextInt(10) % 2 == 0) {
                throw RuntimeException("oops")
            }

            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}