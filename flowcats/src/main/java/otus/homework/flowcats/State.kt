package otus.homework.flowcats

sealed interface State {
    data class Data(val fact: Fact) : State
    data class Error(val ex: Exception) : State
    data object Init : State
}