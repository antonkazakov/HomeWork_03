package otus.homework.flowcats.di

import otus.homework.flowcats.data.CatsRepositoryImpl
import otus.homework.flowcats.data.CatsService
import otus.homework.flowcats.data.converter.CatConverter
import otus.homework.flowcats.domain.CatsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Контейнер зависимостей
 */
class DiContainer {

    /** Репозиторий информации о кошке */
    val repository: CatsRepository by lazy(LazyThreadSafetyMode.NONE) {
        CatsRepositoryImpl(catsService = provideService(), converter = CatConverter())
    }

    private fun provideService(): CatsService = provideRetrofit().create(CatsService::class.java)

    private fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl(FACT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private companion object {
        const val FACT_BASE_URL = "https://cat-fact.herokuapp.com/facts/"
    }
}