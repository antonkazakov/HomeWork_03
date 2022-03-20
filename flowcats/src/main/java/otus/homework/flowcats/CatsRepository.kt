package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result<Fact>> {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
                Log.d("CatsRepository", "${latestNews.createdAt} ${latestNews.text}")
            } catch (e: Exception) {
                emit(Result.Error("Unexpected exception occurred", null))
                Log.d("CatsRepository", "Error - Unexpected exception occurred")
            } finally {
                delay(refreshIntervalMs)
            }
        }
    }
}