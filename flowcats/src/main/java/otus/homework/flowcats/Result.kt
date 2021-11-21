package otus.homework.flowcats

sealed class Result<out T, out E> {
    object Idle: Result<Nothing, Nothing>()
    class Success<T>(val value: T): Result<T, Nothing>()
    class Error<E>(val error: E): Result<Nothing, E>()
}