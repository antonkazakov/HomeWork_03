package otus.homework.flowcats

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    suspend fun listenForCatFacts() = flow {
        while (true) {
            Log.d("myLog", "flow code")
            //val latestNews = catsService.getCatFact()
            val latestNews = fakeGetCatFact()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }

    // api service imitator
    private suspend fun fakeGetCatFact(): Fact {
        delay(2000)
        return Fact(
            createdAt = "",
            deleted = false,
            id = "",
            text = "This is a fact =<^oo^>=",
            source = "",
            used= false,
            type = "",
            user = "",
            updatedAt = ""
        )
    }



}