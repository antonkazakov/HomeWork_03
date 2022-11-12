package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException

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
    }

    suspend fun test(): Fact {
        return try {
            catsService.getCatFact()
        } catch (ex: Exception) {
            Log.d("CATS", "Main server isn't response, try other server. Error: $ex")
            Fact("empty", 1)
        }
    }
}