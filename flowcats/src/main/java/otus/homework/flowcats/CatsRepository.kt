package otus.homework.flowcats

import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException

class CatsRepository(
    private val catsService: CatsService,
) {
    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(e, "Не удалось получить ответ от сервера"))
            } catch (e: Exception) {
                emit(Result.Error(e, e.message.toString()))
            }
        }
    }
}
