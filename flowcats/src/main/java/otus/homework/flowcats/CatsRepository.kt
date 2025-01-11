package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.Result
import java.util.concurrent.TimeoutException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Result.Success(latestNews))
            delay(refreshIntervalMs)
        }
    }.catch {
        println(it)
        emit(Result.Error)
    }
}