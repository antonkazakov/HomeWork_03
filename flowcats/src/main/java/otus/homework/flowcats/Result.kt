package otus.homework.flowcats

sealed class Result {
    class Success<T>(val value: T) : Result()
    object Pending : Result()
    object Error : Result()
}