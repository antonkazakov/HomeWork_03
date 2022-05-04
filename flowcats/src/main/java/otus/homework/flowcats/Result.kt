package otus.homework.flowcats

sealed class Result<out T> {
    class Success<T>(val data: T): Result<T>()
    class Error(val errorText: String?): Result<Nothing>()
}
