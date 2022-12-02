package otus.homework.flowcats

sealed class Result {

    abstract fun getContent(): String

    class Success(private val fact: Fact? = null) : Result() {
        override fun getContent() = fact?.text ?: ""
    }

    class Error(private val message: String) : Result() {
        override fun getContent() = message
    }
}
