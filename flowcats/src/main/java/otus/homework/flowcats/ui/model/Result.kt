package otus.homework.flowcats.ui.model

sealed class Result<T>() {
	class Error<T>(val msg: String) : Result<T>()
	class Success<T>(val data: T) : Result<T>()
	class InProgress<T> : Result<T>()
}