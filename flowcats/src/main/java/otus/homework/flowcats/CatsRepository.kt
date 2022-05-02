package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Error
import java.lang.Exception

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
            }
            catch (error: Exception){
                emit(Error())
            }
        }
    }
}