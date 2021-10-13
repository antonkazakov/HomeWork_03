package otus.homework.flowcats.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import otus.homework.flowcats.utils.Result
import otus.homework.flowcats.api.ApiMapper
import otus.homework.flowcats.api.CatsService
import otus.homework.flowcats.domain.CatModel
import java.lang.Exception

class CatsRepository(
    private val catsService: CatsService,
    private val apiMapper: ApiMapper,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<Result<CatModel>> {
        return flow {
            while (true) {
                try {
                    val latestNews = catsService.getCatFact()
                    emit(Result.Success(apiMapper.toDomain(latestNews)))
                } catch (e: Exception) {
                    emit(Result.Error(e.message ?: "Unknown error"))
                }
                delay(refreshIntervalMs)
            }
        }.flowOn(Dispatchers.IO)
    }
}