package otus.homework.flowcats

import android.content.Context
import otus.homework.coroutines.IResourceProvider
import otus.homework.coroutines.ResourceProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/facts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service by lazy { retrofit.create(CatsService::class.java) }

    val repository by lazy { CatsRepository(service) }

    fun getResources(context: Context): IResourceProvider = ResourceProvider(context)
}