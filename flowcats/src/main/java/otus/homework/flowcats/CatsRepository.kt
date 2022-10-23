package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException

const val TAG = "CatsRepository"

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    /*fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }*/
    fun listenForCatFacts(): Flow<Result<Fact>> = flow<Result<Fact>> {
        while (true) {
            emit(Result.Loading("loading..."))
            emit(loadCatFact())
            delay(refreshIntervalMs)
        }
    }.catch {
        emit(Result.Error(it.toString()))
    }

    private suspend fun loadCatFact(): Result<Fact> {
        return try {
            Result.Success<Fact>(catsService.getCatFact())
        } catch (ex: SocketTimeoutException) {
            //Log.d(TAG, "Main server isn't response, try other server. Error: $ex")
            //catsService.getCatFactReserve().toFact()
            Result.Error<Fact>(ex.toString())
        }
    }

}