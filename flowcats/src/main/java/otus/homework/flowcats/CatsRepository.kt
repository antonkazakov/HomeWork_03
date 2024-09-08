package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val response = catsService.getCatFact()
            val error = response.errorBody()
            val body = response.body()

            val result = when {
                error != null -> Result.Error(error.string())
                body != null -> Result.Success(body)
                else -> Result.Error("Network error")
            }
            emit(result)

            delay(refreshIntervalMs)
        }
    }
}