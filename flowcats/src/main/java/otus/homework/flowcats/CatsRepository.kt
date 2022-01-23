package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import java.lang.IllegalStateException
import kotlin.random.Random

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
//            throw IllegalStateException("TEST")
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.onEach {
        Log.d("CatsRepository", "thread: ${Thread.currentThread().name}")
    }.flowOn(Dispatchers.IO) // to execute listening for facts on IO; next code - on main thread
}