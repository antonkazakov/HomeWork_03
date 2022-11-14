package otus.homework.flowcats

data class Fact(
    val fact: String = "",
    val length: Int = 0
) {
    companion object {
        val EMPTY = Fact()
    }
}
