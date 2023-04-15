package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsNetwork: CatsNetwork,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val source = catsNetwork.getCatFact()
            emit(source)
            delay(refreshIntervalMs)
        }
    }
}