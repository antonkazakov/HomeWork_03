package otus.homework.flowcats

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://aws.random.cat/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service by lazy { retrofit.create(CatsService::class.java) }

    val coroutineDispatchers by lazy { CoroutineDispatchersImpl() }

    val repository by lazy { CatsRepository(service, coroutineDispatchers) }
}