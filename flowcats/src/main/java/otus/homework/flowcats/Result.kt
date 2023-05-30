package otus.homework.flowcats

sealed class Result

object Error : Result()

data class Success(val fact: Fact) : Result()

object Initial : Result ()