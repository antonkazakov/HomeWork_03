package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.handler.Result

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<Result<Fact>> = flow<Result<Fact>> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Result.Success(latestNews))
            delay(refreshIntervalMs)
        }
    }.catch { emit(Result.Error(it)) }
}
