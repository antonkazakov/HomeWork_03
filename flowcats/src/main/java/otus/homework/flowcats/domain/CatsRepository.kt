package otus.homework.flowcats.domain

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий информации о кошке
 */
interface CatsRepository {

    /**
     * Получить периодически обновляемую информацию о кошке [Cat] в виде [Flow]
     *
     * @return информация о Кошке в виде `cancellable` [Flow], на который можно
     * подписываться на любом потоке
     */
    fun listenForCatFacts(): Flow<Cat>
}