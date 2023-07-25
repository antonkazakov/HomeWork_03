package otus.homework.flowcats

sealed class Result{
    class Success(val catsModel: Fact) : Result()
    class Error(val error: Throwable?) : Result()
}
