package otus.homework.flowcats

sealed class Result

class Success<T>: Result()

object Error : Result()
