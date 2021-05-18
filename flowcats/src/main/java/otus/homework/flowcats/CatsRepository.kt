package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact().toResult()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
}

private fun <T> Response<T>.toResult(): Result<T> = when {
    isSuccessful -> Result.Success<T>(body())
    else -> Result.Error(Throwable(errorBody().toString()))
}
