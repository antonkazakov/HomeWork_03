package otus.homework.flowcats

sealed class ResultModel<out T> {
    class Success<out T>(val answer: T) : ResultModel<T>()
    class Error(val exception: Exception) : ResultModel<Nothing>()
}