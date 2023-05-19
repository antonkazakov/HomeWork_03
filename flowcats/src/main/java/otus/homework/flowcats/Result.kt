package otus.homework.flowcats

sealed class Result
class Success(val fact: Fact): Result()
class Error (val throwable: Throwable) : Result()
object InitialValue : Result()