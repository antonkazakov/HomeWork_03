package otus.homework.flowcats

sealed class Result<out T>
class Success<out T>(val data: T) : Result<T>()
object Loading : Result<Nothing>()
object Error : Result<Nothing>()