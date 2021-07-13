package otus.homework.flowcats

sealed class Result {
    data class Success<T>(var data: T) : Result()
    data class Error(var message : String) : Result()
    data class Init(var message : String) : Result()
}
