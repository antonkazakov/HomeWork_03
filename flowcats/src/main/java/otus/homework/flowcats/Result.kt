package otus.homework.flowcats

sealed interface Result<T> {
    @JvmInline
    value class Success<T>(val res: T) : Result<T>

    @JvmInline
    value class Error<T>(val error: Throwable) : Result<T>
}