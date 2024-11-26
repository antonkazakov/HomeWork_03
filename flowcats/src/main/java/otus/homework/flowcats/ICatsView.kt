package otus.homework.flowcats

interface ICatsView {
    fun populate(fact: CatsFact)

    fun showError(message: String)
}
