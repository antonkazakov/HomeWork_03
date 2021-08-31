package otus.homework.flowcats.utils


// out - с ним можно использовать и родительские классы, без него - жесткая привязка к параметру Т
sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Empty : Result<Nothing>()
}