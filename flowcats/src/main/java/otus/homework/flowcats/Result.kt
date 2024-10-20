package otus.homework.flowcats

sealed class Result {
    data object Loading: Result()
    data class Error(val errorMessage: String): Result()
    data class  Success (val content: Fact): Result()
}