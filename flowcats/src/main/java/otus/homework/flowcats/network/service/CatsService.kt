package otus.homework.flowcats.network.service

import otus.homework.flowcats.network.model.FactResponse
import retrofit2.http.GET

interface CatsService {

    @GET("random?animal_type=cat")
    suspend fun getCatFact(): FactResponse
}