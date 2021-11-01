package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5
) {

    fun listenForCatFacts(): Flow<Result<Fact>> {
        return flow {
            while (true) {
                try {
                    val latestNews = catsService.getCatFact()
                    emit(Result.Succsess<Fact>(latestNews))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
                delay(refreshIntervalMs)
            }
        }.flowOn(Dispatchers.IO)
    }
}