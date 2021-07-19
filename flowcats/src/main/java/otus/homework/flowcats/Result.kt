package otus.homework.flowcats

sealed class Result {

    object Empty : Result()

    data class Success(
        val fact: Fact
    ) : Result()

    data class Error(
        val errorMessage: String?
    ) : Result()
}