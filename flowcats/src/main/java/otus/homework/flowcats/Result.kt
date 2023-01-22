package otus.homework.flowcats

sealed class Result  {
    class Success(val result: Fact): Result()
    class Error(val throwable: Throwable?, val errorMessage: String) : Result()}
