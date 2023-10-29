package otus.homework.flowcats

sealed class Result {
    class Error(val throwable: Throwable) : Result()
    class Success(val catFact: Fact?): Result()
}
