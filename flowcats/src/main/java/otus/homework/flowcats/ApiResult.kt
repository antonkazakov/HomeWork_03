package otus.homework.flowcats

sealed class ApiResult<out T> {

    class Success<T>(val data: T): ApiResult<T>()
    class Error(val e: Throwable): ApiResult<Nothing>()

    data object Loading: ApiResult<Nothing>()
}