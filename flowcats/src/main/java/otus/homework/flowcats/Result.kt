package otus.homework.flowcats

sealed class Result
object Loading : Result()
data class Success(val fact: Fact) : Result()
data class Error(val message: String) : Result()
