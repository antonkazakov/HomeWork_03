package otus.homework.flowcats

sealed class Result {
    data class Success<T>(val value: Fact) : Result()
    object Error : Result()
    object Empty : Result()
}