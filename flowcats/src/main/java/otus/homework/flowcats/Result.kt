package otus.homework.flowcats

sealed class Result <out T> {
    data class Success<out T>(val fact: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}