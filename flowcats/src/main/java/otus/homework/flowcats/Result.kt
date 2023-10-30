package otus.homework.flowcats


sealed class Result{

    data class Error<T>(val error: T) : Result()
    data class Success<T>(val fact: T) : Result()
}
