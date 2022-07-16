package otus.homework.flowcats

sealed class Result
data class Error(val errorMessage: String) : Result()
data class Success<T>(val data: T): Result()