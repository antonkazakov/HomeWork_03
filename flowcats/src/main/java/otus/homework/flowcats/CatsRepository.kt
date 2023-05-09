package otus.homework.flowcats

import java.lang.Exception
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    // Думаю можно обернуть в flow.retry или добавить flow.retryWhen для вывода exception,
    // что бы было по красоте
    fun listenForCatFacts() = flow<Result<Fact>> {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (e: Exception) {
                emit(Result.Error(e))
            } finally {
                delay(refreshIntervalMs)
            }
        }
    }.flowOn(ioDispatcher)
}