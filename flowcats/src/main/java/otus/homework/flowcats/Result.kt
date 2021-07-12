package otus.homework.coroutines

/**
 * @author Юрий Польщиков on 12.07.2021
 */
sealed class Result {
    data class Success<T>(val value: T) : Result()
    data class Error(val message: String): Result()
}