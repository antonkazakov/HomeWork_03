package otus.homework.flowcats

sealed class Result
class Success(val fact: Fact): Result()
object Error : Result()
object InitialValue : Result()