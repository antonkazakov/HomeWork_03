package otus.homework.flowcats

sealed class Result<out T : Any> {
    data class Placeholder(val message: String) : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}
