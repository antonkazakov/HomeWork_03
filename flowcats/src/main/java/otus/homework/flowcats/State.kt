package otus.homework.flowcats

sealed class CatsState
object Initial : CatsState()
class Success(val data: Fact) : CatsState()
class Error(val message: String) : CatsState()
