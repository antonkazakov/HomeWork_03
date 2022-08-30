package otus.homework.flowcats

sealed class Result<out T: Any?> {
    data class Success<out T: Any>(val result: T): Result<T>()
    data class Error(val msg: String?, val cause: Throwable): Result<Nothing>()
    data class Loading<out T: Any?>(val dummyResult: T) : Result<T>()
}
