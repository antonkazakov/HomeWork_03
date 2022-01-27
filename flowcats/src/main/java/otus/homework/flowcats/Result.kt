package otus.homework.flowcats

sealed class Result{
    data class Success(val value: Fact): Result()
    data class Error(val error: Throwable? = null): Result()
}