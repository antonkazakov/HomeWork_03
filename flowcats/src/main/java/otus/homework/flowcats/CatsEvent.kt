package otus.homework.flowcats

sealed interface CatsEvent {
    class ShowError(val type: Result.ErrorType) : CatsEvent
}