package otus.homework.flowcats.domain

import otus.homework.flowcats.models.data.FactResponse
import retrofit2.http.GET

interface CatsService {

    @GET("random?animal_type=cat")
    suspend fun getCatFact(): FactResponse
}