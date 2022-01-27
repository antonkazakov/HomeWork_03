package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.SocketTimeoutException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result> {
        while (true) {
            emit(Result.Success(catsService.getCatFact()))
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
        .catch { emit(Result.Error(it) ) }
}