package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            when (val result = doTask()) {
                is Result.Success<*> -> emit(result)
                is Result.Error -> result.e.message?.let { CrashMonitor.trackWarning(it) }
            }
            delay(refreshIntervalMs)
        }
    }

    private suspend fun doTask() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(catsService.getCatFact())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}