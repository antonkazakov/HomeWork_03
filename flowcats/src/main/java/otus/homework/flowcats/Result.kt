package otus.homework.flowcats

sealed class Result {
    class Success<T>(val value: T) : Result()
    class Error(val message: String) : Result()
}