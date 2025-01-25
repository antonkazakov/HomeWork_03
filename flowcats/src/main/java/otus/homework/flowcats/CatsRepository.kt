package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            Log.d("TAGTAG", "listenForCatFacts: ")
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (t: Throwable) {
                emit(Result.Error(t))
            }
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}