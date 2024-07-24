package otus.homework.flowcats

sealed class CatsState {

    object Default : CatsState()
    class CatsSuccess(
        val fact: Fact
    ) : CatsState()

    class CatsError(
        val error: String
    ) : CatsState()
}