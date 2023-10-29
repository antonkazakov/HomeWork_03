package otus.homework.flowcats

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
        .map { Result.Success(it) as Result}
        .catch { emit(Result.Error(it)) }
        .stateIn(scope, SharingStarted.Lazily, null)
}