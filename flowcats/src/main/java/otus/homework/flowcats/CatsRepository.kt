package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            if (latestNews.isSuccessful) {
                if (latestNews.body() != null) {
                    emit(Result.Success(latestNews.body()))
                } else {
                    emit(Result.Error("Body is null"))
                }
            } else {
                emit(Result.Error(latestNews.message()))
            }
            delay(refreshIntervalMs)
        }
    }.catch {
        emit(Result.Error(it.message ?: "Unknown exception"))
    }
}