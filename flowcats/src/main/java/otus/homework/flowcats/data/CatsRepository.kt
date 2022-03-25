package otus.homework.flowcats.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 10000
) {

    fun listenForCatFacts() = flow {
            while (true) {
               emit(catsService.getCatFact())
               delay(refreshIntervalMs)
            }
        }
}