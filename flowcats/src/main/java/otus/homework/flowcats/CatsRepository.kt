package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.data.CatsError
import otus.homework.flowcats.data.Result
import otus.homework.flowcats.data.Success

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Result(latestNews)) // Обработка Success и Error работает, но кажется мне кривоватой.
            //Если есть хорошая практика - напишите мне пожалуйста
            delay(refreshIntervalMs)
        }
    }.catch { exception ->
        emit(CatsError(exception))
    }
}