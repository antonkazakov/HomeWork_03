package otus.homework.flowcats

sealed class Result {

    object Empty : Result()

    class Success(val fact: Fact) : Result()

    class Error(val error: String) : Result()
}
