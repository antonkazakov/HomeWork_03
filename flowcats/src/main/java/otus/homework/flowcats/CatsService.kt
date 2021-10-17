package otus.homework.flowcats

import retrofit2.http.GET

interface CatsService {
    //https://cat-fact.herokuapp.com/facts/
    @GET("random?animal_type=cat")
    suspend fun getCatFact(): Fact
}