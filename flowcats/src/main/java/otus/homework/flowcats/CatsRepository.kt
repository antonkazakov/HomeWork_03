package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result<CatsViewState>> {
        while (true) {
            val fact = catsService.getCatFact()
            emit(Success(CatsViewState(fact)))
            delay(refreshIntervalMs)
        }
    }
        .catch { emit(Error(it.message ?: UNKNOWN_ERROR)) }
        .flowOn(Dispatchers.IO)

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}