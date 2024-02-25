package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import otus.homework.flowcats.Result.Success
import otus.homework.flowcats.Result.Error

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 1000
) {
    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.map { Success(it) as Result}
        .catch {
            emit(Error(it))
        }
}