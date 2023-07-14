package otus.homework.flowcats.model

import otus.homework.flowcats.Fact


sealed class Result { // оптимизирован
    data class Success(val fact: Fact) : Result()

    data class Error(val throwable: Throwable) : Result()
}