package otus.homework.flowcats

import retrofit2.http.GET

interface CatsService {

    @GET("/fact?max_length=140")
    suspend fun getCatFact(): Fact
}