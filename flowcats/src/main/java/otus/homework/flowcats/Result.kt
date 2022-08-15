package otus.homework.flowcats

sealed class Result

data class Success<out T: FactsUiState>(
    val model: T
) : Result()

data class Error(
    val throwable: Throwable
) : Result()

