package otus.homework.flowcats

import java.lang.Exception

sealed class Result()
data class Success(val fact: Fact): Result()
class Error(val ex: Exception): Result()
