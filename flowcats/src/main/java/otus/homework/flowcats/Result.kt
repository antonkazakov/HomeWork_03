package otus.homework.flowcats

sealed class Result {
    class Success(val fact: Fact): otus.homework.flowcats.Result()
    class Error(val message: String): otus.homework.flowcats.Result()
}
