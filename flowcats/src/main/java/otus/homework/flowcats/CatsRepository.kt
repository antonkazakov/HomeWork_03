package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    suspend fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(ResultCats.Success(latestNews))
            } catch (throwable: Exception){
                emit(ResultCats.Error(throwable))
            }

            delay(refreshIntervalMs)
        }
    }
}