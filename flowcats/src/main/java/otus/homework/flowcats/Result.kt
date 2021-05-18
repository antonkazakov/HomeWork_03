package otus.homework.flowcats

/**
 * @author Dmitry Kartsev (Jag)
 * @sinse 16.05.2021
 */
sealed class Result<out T> {
    data class Success<out T>(val value: T?) : Result<T>()
    data class Error<out T>(val cause: Throwable): Result<T>()
}