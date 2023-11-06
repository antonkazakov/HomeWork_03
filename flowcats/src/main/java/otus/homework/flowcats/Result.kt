package otus.homework.flowcats

sealed interface Result<out T> {

    @JvmInline
    value class Error(val message: String): Result<Nothing>

    @JvmInline
    value class Success<T>(val data: T): Result<T>
}