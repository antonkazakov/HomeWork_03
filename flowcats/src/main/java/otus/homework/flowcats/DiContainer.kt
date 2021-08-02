package otus.homework.flowcats

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.*

class DiContainer {

    private val client by lazy {
        OkHttpClient.Builder()
        .connectTimeout(600_000, MILLISECONDS)
        .readTimeout(600_000, MILLISECONDS)
        .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/facts/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val service by lazy { retrofit.create(CatsService::class.java) }

    val repository by lazy { CatsRepository(service) }
}