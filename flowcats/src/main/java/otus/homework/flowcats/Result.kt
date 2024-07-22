package otus.homework.flowcats

sealed class Result
data class Success(val fact: Fact) : Result()
class Error(val throwable: Throwable) : Result()
data object Initial : Result()