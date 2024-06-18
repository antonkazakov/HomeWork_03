package otus.homework.flowcats

import otus.homework.flowcats.model.Fact
import retrofit2.http.GET

interface CatsService {

    @GET("random?animal_type=cat")
    suspend fun getCatFact(): Fact
}