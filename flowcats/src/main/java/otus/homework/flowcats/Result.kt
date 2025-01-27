import androidx.annotation.StringRes

sealed class MyResult<out T> {

    class Success<T>(val data: T) : MyResult<T>()

    class Error(
        @StringRes val errorResId: Int? = null,
        val errorMsg: String? = null
    ) : MyResult<Nothing>()
}