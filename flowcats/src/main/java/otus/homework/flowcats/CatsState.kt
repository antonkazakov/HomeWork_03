package otus.homework.flowcats

data class CatsState(
    val fact: Fact?
) {
    companion object {
        fun default() = CatsState(
            fact = null
        )
    }
}