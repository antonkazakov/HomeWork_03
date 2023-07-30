package otus.homework.flowcats.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import otus.homework.flowcats.data.converter.CatConverter
import otus.homework.flowcats.data.models.Fact
import otus.homework.flowcats.domain.Cat
import otus.homework.flowcats.domain.CatsRepository

/**
 * Репозиторий информации о кошке [CatsRepository]
 *
 * @param catsService сервис получения информации о кошках
 * @param refreshIntervalMs период обновления информации о коте в ms
 * @param converter конвертер данных из [Fact] в данные с информацией о кошке [Cat]
 */
class CatsRepositoryImpl(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000,
    private val converter: CatConverter
) : CatsRepository {

    override fun listenForCatFacts(): Flow<Cat> = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.map { fact -> converter.convert(fact) }
        .flowOn(Dispatchers.IO)
}