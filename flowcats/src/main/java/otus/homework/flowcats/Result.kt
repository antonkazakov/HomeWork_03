package otus.homework.flowcats

sealed interface Result<out T : Any>
data class Success<T : Any>(val state: T) : Result<T>
data class Error(val message: String) : Result<Nothing>
object Loading : Result<Nothing>