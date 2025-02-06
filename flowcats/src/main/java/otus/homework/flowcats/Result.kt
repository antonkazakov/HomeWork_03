package otus.homework.flowcats

sealed class Result {
    data class Success<T>(val data: T) : otus.homework.flowcats.Result()
    data class Error(val message: String?, val throwable: Throwable? = null) : otus.homework.flowcats.Result()
}