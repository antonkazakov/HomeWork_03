package otus.homework.flowcats

sealed class Result<T>{
    data class Success<T>(val value: T): Result<T>()
    data class Error(val message: String?, val throwable: Throwable?): Result<Nothing>()
}
