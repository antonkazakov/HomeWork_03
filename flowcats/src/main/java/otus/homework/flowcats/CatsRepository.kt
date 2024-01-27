package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    private var count = 0

    fun listenForCatFacts() = flow {
        while (true) {
            count++
            if(count % 3 == 0) {
                throw RuntimeException("Test error")
            }

            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
}