package otus.homework.flowcats

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result<Fact>> {
        while (true) {
            runCatching { catsService.getCatFact() }
                .onSuccess { latestNews ->
                    emit(Result.Success(latestNews))
                }
                .onFailure { error ->
                    emit(Result.Error(error))
                }
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}