package otus.homework.flowcats.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import otus.homework.flowcats.models.domain.Fact
import otus.homework.flowcats.models.domain.Result

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Fact(latestNews.text))
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
}