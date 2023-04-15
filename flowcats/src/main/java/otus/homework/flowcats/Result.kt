package otus.homework.flowcats

sealed class Result

data class Success(val fact: Fact) : Result()
object Error : Result()
object Loading : Result()
