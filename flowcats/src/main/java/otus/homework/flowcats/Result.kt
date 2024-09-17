package otus.homework.flowcats

sealed class Result{

    data class Success<T>(
        val data: T
    ): Result()

    object Error: Result()
}
