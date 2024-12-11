package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

//    fun listenForCatFacts() = flow {
//        while (true) {
//            val latestNews = catsService.getCatFact()
//            emit(latestNews)
//            delay(refreshIntervalMs)
//        }
//    }

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (ex: SocketTimeoutException){
                emit(Result.Error("Не удалось получить ответ от сервера"))
            } catch (ex: Exception){
                if (ex is CancellationException) throw ex
                emit(Result.Error(ex.message ?: ex.toString()))
            }
            delay(refreshIntervalMs)
        }
    }
}