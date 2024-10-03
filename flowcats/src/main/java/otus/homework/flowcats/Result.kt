package otus.homework.flowcats

sealed class Result
data class Success(val cat: Fact): Result()
data class Error(val error:String): Result()