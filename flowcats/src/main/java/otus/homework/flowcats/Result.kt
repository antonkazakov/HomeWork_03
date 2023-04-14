package otus.homework.flowcats

sealed class Result<T> {
    object Loading: Result<Any?>()
    class Success(val fact: Fact): Result<Any?>()
    class Error(val exception: Throwable): Result<Any?>()
}