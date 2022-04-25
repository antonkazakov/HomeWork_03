package otus.homework.flowcats

sealed class Result<out T> {
    data class Empty(val message:String) : Result<Nothing>()
    data class Success<T>(val result: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}