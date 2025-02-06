package otus.homework.flowcats

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(Level.BODY);
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();
        return client
    }

    private val catsFactRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }


    val service by lazy { catsFactRetrofit.create(CatsService::class.java) }

    val repository by lazy { CatsRepository(service) }
}