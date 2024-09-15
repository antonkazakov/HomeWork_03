package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                if (latestNews.isSuccessful) {
                    emit(Result.Success<Fact>(latestNews.body()!!))
                } else {
                    emit(Result.Error(IOException("Error ${latestNews.errorBody()}")))
                }
            } catch (ex: Exception) {
                emit(Result.Error(IOException("Error ${ex}")))
            }

            delay(refreshIntervalMs)
        }
    }
}