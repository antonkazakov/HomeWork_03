package otus.homework.flowcats

sealed class State {
    data object Initial : State()
    data class Success(val text: String) : State()
    data class Error(val text: String) : State()
}