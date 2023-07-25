package otus.homework.flowcats

sealed class MainState<out T> {

    data class Success<T>(val item: T) : MainState<T>()

    object Error : MainState<Nothing>()
}
