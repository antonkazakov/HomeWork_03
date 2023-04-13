package otus.homework.flowcats

sealed class Result
object Start : Result()
data class Success(val items: Fact) : Result()
class Error(val error: Throwable) : Result()