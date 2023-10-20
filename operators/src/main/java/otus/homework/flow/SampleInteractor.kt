package otus.homework.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
class SampleInteractor(
    private val sampleRepository: SampleRepository
) {

    /**
     * Реализуйте функцию task1 которая последовательно:
     * 1) умножает числа на 5
     * 2) убирает чила <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    fun task1(): Flow<String> {
        return sampleRepository.produceNumbers()
            .map { number -> number * 5 }
            .filter { number -> number > 20 }
            .filter { number -> number % 2 != 0 }
            .take(3)
            .flatMapLatest { number -> flow { emit("$number won") } }
//            .flatMapWith("won")
            .flowOn(Dispatchers.Default)
    }

//    private fun <Int> Flow<Int>.flatMapWith(text: String): Flow<String> {
//        return transform {number ->
//            emit("$number $text")
//        }
//    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    fun task2(): Flow<String> {
        return sampleRepository.produceNumbers()
            .flatMapLatest { number ->
                flow {
                    when {
                        (number % 15 == 0) -> {
                            emit("$number")
                            emit("FizzBuzz")
                        }

                        (number % 5 == 0) -> {
                            emit("$number")
                            emit("Buzz")
                        }

                        (number % 3 == 0) -> {
                            emit("$number")
                            emit("Fizz")
                        }

                        else -> {
                            emit("$number")
                        }
                    }
                }
            }
            .flowOn(Dispatchers.Default)
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> {
        val colors = sampleRepository.produceColors()
        val forms = sampleRepository.produceForms()
        return colors.zip(forms) { color, form -> Pair(color, form) }
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return flow {
            try {
                val numbers = sampleRepository.produceNumbers()
                emitAll(numbers)
            } catch (e: IllegalArgumentException) {
                emit(-1)
            } catch (e: Exception) {
                throw e
            } finally {
                sampleRepository.completed()
            }
        }
    }
}