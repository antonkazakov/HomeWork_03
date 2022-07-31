package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result<Fact>> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Result.Success(latestNews))
            delay(refreshIntervalMs)
        }
    }.catch { e ->
        Log.d(this@CatsRepository.javaClass.canonicalName, "Error", e)
        emit(Result.Error(e.message ?: "Error"))
    }
}

/*
* Fact(
                createdAt = "123",
                deleted = false,
                id = "123",
                text = Random.nextInt().toString(),
                source = "123",
                used = false,
                type = "123",
                user = "123",
                updatedAt = "123"
            )
* */