package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<ApiResult<Fact>> = flow {
        while (true) {
            emit(ApiResult.Loading)
            val latestNews = try {
                val fact = catsService.getCatFact()
                ApiResult.Success(fact)
            } catch (e: Exception){
                ApiResult.Error(e)
            }
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
}