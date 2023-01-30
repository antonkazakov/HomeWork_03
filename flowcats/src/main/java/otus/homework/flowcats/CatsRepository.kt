package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        try {
            while (true) {
                val latestNews = catsService.getCatFact()
                //throw Exception()
                emit(Success(latestNews))
                delay(refreshIntervalMs)
            }
        }catch (e: Exception){
            emit(Error)
        }

    }.flowOn(Dispatchers.IO)
}