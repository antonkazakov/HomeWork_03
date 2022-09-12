package otus.homework.flowcats

sealed class Result {

    class Success<T>(val data: T?) : Result()

    class Error(val errorMessage: String) : Result()
}