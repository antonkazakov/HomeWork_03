package otus.homework.flowcats

sealed class Result {
    data class Success<T>(val body: T) : Result()
    object Error : Result()
    object Empty : Result()
}