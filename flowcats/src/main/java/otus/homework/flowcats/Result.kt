package otus.homework.flowcats

sealed interface Result {

  class Success(val fact: Fact) : Result

  data object Error : Result

  data object Loading : Result
}
