package otus.homework.flowcats.data

import otus.homework.flowcats.data.models.Fact
import retrofit2.http.GET

/**
 * Сервис получения информации о кошках
 */
interface CatsService {

    /** Получить случайных факт о кошке [Fact] */
    @GET("random?animal_type=cat")
    suspend fun getCatFact(): Fact
}