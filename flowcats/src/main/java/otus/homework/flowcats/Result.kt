package otus.homework.flowcats

sealed class Result {
    data class Success<T>(val fact: T?): Result()
    data class Error(val errorMessage: String): Result()
}