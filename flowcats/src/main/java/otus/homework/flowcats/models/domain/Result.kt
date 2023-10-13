package otus.homework.flowcats.models.domain

sealed class Result {
    data class Success(val value: Fact) : Result()
    data class Error(val exception: Throwable) : Result()
}
