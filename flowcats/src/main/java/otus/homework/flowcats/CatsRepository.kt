package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val bookService: BookService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = bookService.getBook()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
}