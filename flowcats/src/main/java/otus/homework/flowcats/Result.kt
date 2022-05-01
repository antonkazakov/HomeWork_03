package otus.homework.flowcats

sealed class Result<out T : Any> {
    object StartLoading: Result<Nothing>()
    data class Success <out T : Any>(val value: Fact): Result<T>()
    data class Error (
        val throwable: Throwable?
    ): Result<Nothing>()
}
