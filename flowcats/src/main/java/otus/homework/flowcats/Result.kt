package otus.homework.flowcats

sealed class Result<out T> {
    data object Init : Result<Nothing>()
    class Success<T>(val fact: T): Result<T>()
    class Error(val msgError: String?) : Result<Nothing>()
}