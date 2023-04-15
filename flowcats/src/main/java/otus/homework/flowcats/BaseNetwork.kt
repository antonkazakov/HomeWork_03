package otus.homework.flowcats

import retrofit2.Response

abstract class BaseNetwork {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    error(Exception("Data error"))
                }
            }else {
                error(Exception("Unknown Error"))
            }
        } catch (ex: Exception) {
            error(ex)
        }
    }

    private fun <T> error(message: Exception): Result<T> {
        return Result.Error(message)
    }

}