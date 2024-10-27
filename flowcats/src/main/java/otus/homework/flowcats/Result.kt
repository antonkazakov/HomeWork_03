package otus.homework.flowcats


sealed interface Result<T> {
    data class Success<T>(val data: T): Result<T>
    data class Error<T>(val error: Throwable): Result<T>
    class Nothing<T>: Result<T>
}