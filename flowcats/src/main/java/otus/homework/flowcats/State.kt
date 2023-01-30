package otus.homework.flowcats

sealed interface State
data class Success(val item: Fact): State
object Error: State
