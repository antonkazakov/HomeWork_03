package otus.homework.flowcats.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.network.service.CatsService
import otus.homework.flowcats.ui.model.Fact

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
):ICatRepository {

    override fun listenForCatFacts(): Flow<Fact> = flow {
        while (true) {
            val latestNews = catsService.getCatFact().toFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }

}