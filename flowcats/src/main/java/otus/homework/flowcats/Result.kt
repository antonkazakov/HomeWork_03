package otus.homework.flowcats


sealed class Result()
data class Success(var data: Fact) : Result()
data class Errors(var err: String) : Result()
data class Loading(var str: String) : Result()
