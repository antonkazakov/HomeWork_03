package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface Repository {
    suspend fun listenForCatFacts() : Flow<LoadResult>
}

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) : Repository {

    override suspend fun listenForCatFacts(): Flow<LoadResult> {
        return flow {
            while (true){
                try {
                    val latestNews = catsService.getCatFact()
                    emit(LoadResult.Success(latestNews.text))
                    delay(refreshIntervalMs)
                } catch (e: Exception) {
                    emit(LoadResult.Error(e.message ?: "error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}