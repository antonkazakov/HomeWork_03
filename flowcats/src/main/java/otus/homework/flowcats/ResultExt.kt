package otus.homework.flowcats

inline fun <reified T> Result<T>.doIfError(callback: (error: String) -> Unit) {
    if (this is Result.Error) {
        callback(message)
    }
}

inline fun <reified T> Result<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is Result.Success) {
        callback(fact)
    }
}

inline fun <reified T> Result<T>.doIfEmpty(callback: (value: String) -> Unit) {
    if (this is Result.Empty) {
        callback(emptyString)
    }
}