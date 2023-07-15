package otus.homework.flowcats

sealed class Result {
    data class Success(val catFact: Fact) : Result()
    object Loading : Result()
    data class Error(val throwable: Throwable) : Result()
}
