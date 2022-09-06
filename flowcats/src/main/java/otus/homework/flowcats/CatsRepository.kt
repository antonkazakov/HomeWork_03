package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    suspend fun listenForCatFacts(): Flow<Result> {
        return flow<Result> {
            while (true) {
                val latestNews = catsService.getCatFact()
                Log.d("TEST", latestNews.text)
                emit(Result.Success(latestNews))
                delay(refreshIntervalMs)
            }
        }.flowOn(Dispatchers.IO).catch {
            flow { emit(Result.Error("Error")) }
        }
    }

}