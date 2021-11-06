package otus.homework.flowcats

sealed class CatResultModel {
    class Success<out T>(val answer: T) : CatResultModel()
    class Error(val message: String) : CatResultModel()
}