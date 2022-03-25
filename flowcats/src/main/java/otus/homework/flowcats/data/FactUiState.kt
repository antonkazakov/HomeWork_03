package otus.homework.flowcats.data

//sealed class Result<out R> {
//    object Loading : Result<Nothing>()
//    data class Success<out T>(val data: T) : Result<T>()
//    data class Error(val exception: Exception) : Result<Nothing>()
//}

sealed class FactUiState {
    object Loading: FactUiState()
    data class Success(val fact: Fact): FactUiState()
    data class Error(val exception: Throwable): FactUiState()
}