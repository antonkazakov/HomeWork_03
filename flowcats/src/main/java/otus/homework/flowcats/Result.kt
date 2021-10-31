package otus.homework.flowcats

sealed class Result

class Success<T>(val data: T): Result()
class Error(val error: String) : Result()
