package otus.homework.flowcats

sealed class Result {
    object Error: Result()
    object Success: Result()
}
