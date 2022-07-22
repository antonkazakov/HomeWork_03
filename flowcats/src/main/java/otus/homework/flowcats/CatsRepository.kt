package otus.homework.flowcats

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        null
    )
}