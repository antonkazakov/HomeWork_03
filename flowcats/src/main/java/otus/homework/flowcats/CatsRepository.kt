package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            emit(getCats())
            delay(refreshIntervalMs)
        }
    }

    suspend fun getCats(): Resp {
        return try {
            Resp.Success(catsService.getCatFact())
        } catch (ex: Exception) {
            Resp.Error(ex)
        }
    }
}