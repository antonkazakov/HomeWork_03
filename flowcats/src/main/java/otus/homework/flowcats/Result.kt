package otus.homework.flowcats


sealed class Result<out T>

class Success<T>(val value: T) : Result<T>() {
  companion object {
    val EMPTY = Success(Fact.EMPTY_FACT)
  }
}

class Error(val message: String) : Result<Nothing>()