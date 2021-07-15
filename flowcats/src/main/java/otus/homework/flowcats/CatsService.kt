package otus.homework.flowcats

import retrofit2.http.GET

interface CatsService {

    @GET("dogs?number=1")
    suspend fun getCatFact(): List<Fact>
}