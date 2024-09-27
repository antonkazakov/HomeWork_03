package otus.homework.flowcats

import androidx.annotation.StringRes
import otus.homework.flowcats.data.Book

sealed class Result {
    class Success(val book: Book?): Result()
    class Error(val message: String? = null, @StringRes val messageRes: Int? = null): Result()
}