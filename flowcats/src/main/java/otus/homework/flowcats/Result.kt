package otus.homework.flowcats

sealed interface Result<out T> {
    class Success<T>(val data: T) : Result<T>
    class Error(val type: ErrorType) : Result<Nothing>

    sealed interface ErrorType {
        object UnknownError : ErrorType
    }

    companion object {
        fun unknownError() = Error(ErrorType.UnknownError)
    }
}