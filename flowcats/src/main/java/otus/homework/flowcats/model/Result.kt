package otus.homework.flowcats.model

sealed class Result<T> (private val value : T) {
    fun get(): T {
        return value
    }
}