package otus.homework.flowcats

import MyResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<MyResult<Fact>> = flow {
        while (true) {
            val factCat = try {
                MyResult.Success(catsService.getCatFact())
            } catch (t: Throwable) {
                MyResult.Error(errorMsg = t.message)
            }
            emit(factCat)
            delay(refreshIntervalMs)
        }
    }
}