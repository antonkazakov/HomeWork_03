package otus.homework.flowcats

sealed class Result<T> {

    class Success<T>(
        val response: T
    ): Result<T>()

    class Error<T>(
        val message: String
    ): Result<T>()
}