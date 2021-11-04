package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<Result<Fact>> = flow {
        while (true) {
            val latestNews = toResult { catsService.getCatFact() }
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}