package otus.homework.flowcats

import retrofit2.http.GET

interface CatsService {

    @GET("meow")
    suspend fun getCatImage(): Image
}