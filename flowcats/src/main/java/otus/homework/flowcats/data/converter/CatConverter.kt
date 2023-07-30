package otus.homework.flowcats.data.converter

import otus.homework.flowcats.data.models.Fact
import otus.homework.flowcats.domain.Cat

/**
 * Конвертер данных из [Fact] в данные с информацией о кошке [Cat]
 */
class CatConverter {

    /** Сконвертировать факт [Fact] в информацию о кошке [Cat] */
    fun convert(fact: Fact) = Cat(fact = fact.text)
}