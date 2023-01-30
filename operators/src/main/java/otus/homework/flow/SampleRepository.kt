package otus.homework.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface SampleRepository {

    fun produceNumbers(): Flow<Int>
    fun produceColors(): Flow<String>

    fun produceForms(): Flow<String>

    fun completed()
}