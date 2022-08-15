package otus.homework.flowcats

sealed class FactsUiState

data class Signature(
    val text: String
) : FactsUiState()

object Loading : FactsUiState()