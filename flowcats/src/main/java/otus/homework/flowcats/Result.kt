package otus.homework.flowcats

sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
    data class Initial<T>(val initialMessage: String = "There no cat facts") : Result<T>()
}
