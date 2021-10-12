package otus.homework.flowcats

sealed class Result<out T> {
    data class Success<out R>(val fact : R) : Result<R>()
    data class Error(val message: String) : Result<Nothing>()
    data class Empty(val emptyString: String) : Result<Nothing>()
}