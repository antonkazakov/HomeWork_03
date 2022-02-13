package otus.homework.flowcats

sealed class Result<T>

class Success<T>(val result: T? = null) : Result<T>()

class Error<T>(t: Throwable) : Result<T>()