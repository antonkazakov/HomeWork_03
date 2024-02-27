package otus.homework.flowcats

sealed class Result

data object Initial: Result()
data class Success(val fact: Fact): Result()
data class Error(val error: String): Result()
