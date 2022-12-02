package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(latestNews)
            } catch (exception: HttpException) {
                exception.message?.let { message ->
                    emit(Fact(message, ""))
                }
            }
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}