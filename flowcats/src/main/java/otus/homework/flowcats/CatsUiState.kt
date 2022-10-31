package otus.homework.flowcats

sealed class CatsUiState {
    data class Success(val catFact: CatFact) : CatsUiState()
    data class Error(val exception: String) : CatsUiState()
}
