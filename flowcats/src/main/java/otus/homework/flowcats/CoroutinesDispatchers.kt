package otus.homework.flowcats

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val ioDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val computationDispatcher: CoroutineDispatcher
}