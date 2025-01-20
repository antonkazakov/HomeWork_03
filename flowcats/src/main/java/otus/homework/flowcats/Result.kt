package otus.homework.flowcats

sealed class Result {
    data object Nothing : Result()
    class Success(val fact: Fact) : Result()
    class Error(val message: String) : Result()
}