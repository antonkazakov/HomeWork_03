package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = getResult(catsService)
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }

    private suspend fun getResult(service: CatsService): Result<Any> {
        val result = service.getCatFact()
        val responseBody = result.body()
        return try {
            if (result.isSuccessful && responseBody != null) Success(responseBody)
            else Error(result.code(), result.message())
        } catch (e: IOException) {
            ResultException(e)
        }

    }

}

