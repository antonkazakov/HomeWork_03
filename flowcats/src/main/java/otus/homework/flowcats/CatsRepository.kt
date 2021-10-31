package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Fact?> {
            while (true) {
                emit(catsService.getCatFact())
                delay(refreshIntervalMs)
            }
        }
        //можно эмитить например специальную разновидность факта с какими-то данными связанными с ошибкой
        .catch { emit(null)}
        .flowOn(Dispatchers.IO)
}
