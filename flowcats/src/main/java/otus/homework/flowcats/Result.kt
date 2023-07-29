package otus.homework.flowcats

import android.util.Log

sealed class Result<out T>(val status: Status, val data: T?) {

    class Success<T>(data: T) : Result<T>(Status.SUCCESS, data)

    class Error<T>(throwable: Throwable) : Result<T>(Status.ERROR, null) {
        init {
            Log.w("CatsResult`", throwable.message ?: "", throwable)
        }

        val exceptionMessage: String? = throwable.message
    }

    companion object {
        enum class Status {
            SUCCESS,
            ERROR
        }
    }
}
