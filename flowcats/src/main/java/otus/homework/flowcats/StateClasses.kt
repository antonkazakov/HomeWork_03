package otus.homework.flowcats

sealed class Result<T>

class Success<T>(val result: T?) : Result<T>()

class Error<T>(t: Throwable) : Result<T>()