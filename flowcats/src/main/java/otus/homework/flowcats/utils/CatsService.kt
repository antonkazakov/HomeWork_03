package otus.homework.flowcats.utils

import otus.homework.flowcats.model.Fact
import retrofit2.http.GET

interface CatsService {

    @GET("fact")
    suspend fun getCatFact(): Fact
}