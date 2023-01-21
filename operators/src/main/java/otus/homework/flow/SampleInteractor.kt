package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SampleInteractor(
    private val sampleRepository: SampleRepository
) {

    /**
     * Реализуйте функцию task1 которая последовательно:
     * 1) умножает числа на 5
     * 2) убирает чиcла <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    suspend fun task1(): Flow<String> {
        val result: Array<String> = sampleRepository.produceNumbers().toList().asSequence()
            .map { it * 5 }.filter { it > 20 }.filter { it % 2 != 0 }.map { "$it won" }.take(3)
            .toList().toTypedArray()

        return flowOf(*result)
    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    suspend fun task2(): Flow<String> {
        val list = sampleRepository.produceNumbers().toList()
        val resultList: MutableList<String> = mutableListOf()

        for (i in list) {
            var value = i.toString()
            when {
                i % 15 == 0 -> {
                    resultList.add(value)
                    value = "FizzBuzz"
                }
                i % 3 == 0 -> {
                    resultList.add(value)
                    value = "Fizz"
                }
                i % 5 == 0 -> {
                    resultList.add(value)
                    value = "Buzz"
                }
            }
            resultList.add(value)
        }
        return flowOf(*resultList.toTypedArray())
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 - айтем из первого флоу, f2 - айтем из второго флоу.
     * Если айтемы в одном из флоу кончились, то результирующий флоу также должен закончиться
     */
    suspend fun task3(): Flow<Pair<String, String>> {
        val flow1: Flow<String> = sampleRepository.produceColors()
        val flow2: Flow<String> = sampleRepository.produceForms()
        val resultList: MutableList<Pair<String, String>> = mutableListOf()

        flow1.zip(flow2) { f1, f2 -> Pair(f1, f2) }.collect {
            resultList.add(it)
        }
        return flowOf(*resultList.toTypedArray())
    }

    /**
     * Реализуйте функцию task4, которая обрабатывает IllegalArgumentException и в качестве коллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции, вызовите метод dotsRepository.completed()
     */
    suspend fun task4(): Flow<Int> {
        val resultList: MutableList<Int> = mutableListOf()
        try {
            sampleRepository.produceNumbers().collect {
                resultList.add(it)
            }
        } catch (e: Exception) {
            if (e is IllegalArgumentException)
                resultList.add(-1)
            else
                throw e
        } finally {
            sampleRepository.completed()
        }
        return flowOf(*resultList.toTypedArray())
    }
}