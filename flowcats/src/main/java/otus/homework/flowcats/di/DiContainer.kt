package otus.homework.flowcats.di

import otus.homework.flowcats.network.service.CatsService
import otus.homework.flowcats.repository.CatRepositoryMock
import otus.homework.flowcats.repository.CatsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/facts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service by lazy { retrofit.create(CatsService::class.java) }

    val repository by lazy { CatsRepository(service) }
    val repositoryMock by lazy { CatRepositoryMock() }
}