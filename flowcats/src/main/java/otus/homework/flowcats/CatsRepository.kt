package otus.homework.flowcats

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<Result<Fact>> = flow<Result<Fact>> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Success(latestNews))
            delay(refreshIntervalMs)
        }
    }.catch {
        emit(Error)
        emitAll(listenForCatFacts())
    }.flowOn(backgroundDispatcher)
}