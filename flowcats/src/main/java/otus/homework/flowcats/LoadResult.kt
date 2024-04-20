package otus.homework.flowcats

interface LoadResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(fact: String): T
        fun mapLoading(message: String): T
        fun mapError(message: String): T
    }

    data class Success(private val fact: String) : LoadResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(fact)
        }
    }

    data class Loading(private val message: String) : LoadResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapLoading(message)
        }
    }

    data class Error(private val message: String) : LoadResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError(message)
        }
    }
}

class ResultMapper() : LoadResult.Mapper<ICatsUiState> {

    override fun mapSuccess(fact: String): ICatsUiState {
        return ICatsUiState.Success(fact)
    }

    override fun mapLoading(message: String): ICatsUiState {
        return ICatsUiState.Loading(message)
    }

    override fun mapError(message: String): ICatsUiState {
        return ICatsUiState.Error(message)
    }
}