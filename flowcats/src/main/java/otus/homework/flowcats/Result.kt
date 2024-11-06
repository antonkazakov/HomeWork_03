package otus.homework.flowcats

sealed class Result<out T> {
    data object Loading: Result<Nothing>()

    data class Success<T>(val data: T): Result<T>()

    class Error(val message: String?) : Result<Nothing>()
}