package otus.homework.flowcats

sealed class Result {
    data class Success(val fact: Fact): Result()

   data object Error: Result()

    data object Initial: Result()

    data object Loading: Result()


}