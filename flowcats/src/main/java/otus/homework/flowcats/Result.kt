package otus.homework.flowcats

sealed class Result

data class Success<T>(val value : T): Result()
data class Error(val message: String): Result()
object Loading: Result()