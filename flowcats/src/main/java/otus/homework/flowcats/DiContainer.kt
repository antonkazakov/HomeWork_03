package otus.homework.flowcats

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://fakerapi.it/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service by lazy { retrofit.create(BookService::class.java) }

    val repository by lazy { CatsRepository(service) }
}