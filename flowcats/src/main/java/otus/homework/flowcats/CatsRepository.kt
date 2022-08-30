package otus.homework.flowcats

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<Result<Fact?>> = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (ex: Exception) {
                emit(
                    when (ex) {
                        is CancellationException -> throw ex
                        else -> Result.Error(ex.message, ex)
                    }
                )
            }
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}