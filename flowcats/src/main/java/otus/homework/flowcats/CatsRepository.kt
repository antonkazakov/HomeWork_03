package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.model.Result

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            // эмитим соответствующий результат запросы
            try {
                // ошибка подключения не поломает приложение
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (exception : Exception) {
                emit(Result.Error(exception))
            }
            delay(refreshIntervalMs)
        }
    }
}