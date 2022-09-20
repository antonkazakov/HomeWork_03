package otus.homework.flowcats

import java.net.SocketTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(latestNews)
                delay(refreshIntervalMs)
            } catch (e: SocketTimeoutException) {
                emit(e)
            }
        }
    }
}