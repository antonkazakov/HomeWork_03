package otus.homework.flowcats

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Result<T>(data)
    class Error<T>(message: String, data: T?): Result<T>(message = message, data = data)
}