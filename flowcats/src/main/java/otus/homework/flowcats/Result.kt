package otus.homework.flowcats


sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Empty : Result<Nothing>()
}