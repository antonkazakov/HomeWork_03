package otus.homework.flowcats

sealed class Result {
    class Success<out T>(val data: T) : Result()
    data object Error : Result()
    data object Init : Result()
}
