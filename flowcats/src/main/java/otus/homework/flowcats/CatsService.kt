package otus.homework.flowcats

import retrofit2.http.GET

interface CatsService {

    @GET(".")
    suspend fun getCatFact(): Fact
}