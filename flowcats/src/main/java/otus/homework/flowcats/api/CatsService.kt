package otus.homework.flowcats.api

import retrofit2.http.GET

interface CatsService {

    @GET("random?animal_type=cat")
    suspend fun getCatFact(): Fact
}