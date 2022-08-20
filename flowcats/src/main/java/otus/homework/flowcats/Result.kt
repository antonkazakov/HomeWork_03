package otus.homework.flowcats

sealed class Result {
    data class Success(val data: Fact?) : Result()
    data class Error(val message: String) : Result()
}