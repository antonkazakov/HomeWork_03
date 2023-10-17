package otus.homework.flowcats

sealed interface Result {
    data class Success(val fact: Fact) : Result
    data class Error(val ex: Exception) : Result
}
