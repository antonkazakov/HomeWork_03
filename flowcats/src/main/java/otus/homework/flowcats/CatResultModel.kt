package otus.homework.flowcats

sealed class CatResultModel<out T> {
    class Success<out T>(val answer: T) : CatResultModel<T>()
    class Error(val exception: Exception) : CatResultModel<Nothing>()
}