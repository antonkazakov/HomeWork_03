package otus.homework.flowcats.model

sealed class Result {

    class Success<T>(val data: T) : Result()

    class Error(val error: Exception) : Result()

}