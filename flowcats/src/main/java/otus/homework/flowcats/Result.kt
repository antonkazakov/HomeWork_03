package otus.homework.flowcats

sealed class Result<out T> {
    object Initial : Result<Nothing>()
    class Success<T>(val data: T) : Result<T>()
    class Error(val message: String) : Result<Nothing>()
}