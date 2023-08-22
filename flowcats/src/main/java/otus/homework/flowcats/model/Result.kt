package otus.homework.flowcats.model


sealed class Result<out T> {
    class Success<T>(val fact: T) : Result<T>()

    class Error(val throwable: Throwable) : Result<Throwable>()
}