package otus.homework.flowcats

sealed class Result

class Success(val data: Fact) : Result()

class Error(val errorCode: Int?, val errorMessage: String?) : Result()

class ResultException(val throwable: Throwable) : Result()
