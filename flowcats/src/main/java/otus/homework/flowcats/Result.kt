package otus.homework.flowcats

sealed class Result<out T> {
    object Idle : Result<Nothing>()
    class Success<T>(val data: T) : Result<T>()
    class Error(val error: Throwable) : Result<Nothing>()
}
