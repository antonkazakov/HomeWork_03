package otus.homework.flowcats

sealed class Result {
    class Success<out T>(val answer: T) : Result()
    class Error(val message: String) : Result()
}