package otus.homework.flowcats

import java.lang.Exception

sealed class Result<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Error(val error: Exception) : Result<Nothing>()
}

suspend fun <T> toResult(result: suspend () -> T): Result<T> = try {
    Result.Success(result())
} catch (e: Exception) {
    Result.Error(e)
}