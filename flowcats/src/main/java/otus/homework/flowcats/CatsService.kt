package otus.homework.flowcats

import retrofit2.http.GET

interface CatsService {

    @GET("facts/")
    suspend fun getCatFact(): CatFactResponse
}