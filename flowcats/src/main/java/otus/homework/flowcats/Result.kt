package otus.homework.flowcats

sealed class Result<T : Any?>

class Success<T : Any>(val data: Fact) : Result<T>()

class Error<T : Any>(val errorCode: Int?, val errorMessage: String?) : Result<T>()

class ResultException<T : Any>(val throwable: Throwable) : Result<T>()
