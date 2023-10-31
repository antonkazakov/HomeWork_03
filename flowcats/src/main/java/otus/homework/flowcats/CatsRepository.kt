package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            runCatching {
                Log.d("CatsRepository", catsService.getCatFact().toString())
                val latestNews = catsService.getCatFact()
            emit(latestNews)
            }.onFailure {
                Log.d("CatsRepository", it.toString())
            }
            delay(refreshIntervalMs)
        }
    }
}