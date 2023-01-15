package otus.homework.flowcats

sealed class Result {
  object Initial : Result()
  data class Success(val fact: Fact) : Result()
  data class Error(val message: String) : Result()
}
