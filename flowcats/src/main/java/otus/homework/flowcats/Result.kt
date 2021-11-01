package otus.homework.flowcats

sealed class Result< out T> {
    data class Succsess< out T>(val fact: Fact) : Result<T>()
    data class Error(val e: Throwable) : Result<Nothing>()
    object Empty : Result<Nothing>()
}
