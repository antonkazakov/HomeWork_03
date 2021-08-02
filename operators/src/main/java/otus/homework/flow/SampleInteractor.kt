package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SampleInteractor(
    private val sampleRepository: SampleRepository
) {

    /**
     * Реализуйте функцию task1 которая последовательно:
     * 1) возводит числа в 5ую степень
     * 2) убирает чила <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    fun task1(): Flow<String> {
        return flowOf(7, 12, 4, 8, 11, 5, 7, 16, 99, 1)
            .map { it * 5 } // по условию задачи тут должно быть - map { it.toDouble().pow(5).toInt() }
            .filter { it > 20 }
            .filter { it % 2 != 0 }
            .map { "$it won" }
            .take(3)
    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    fun task2(): Flow<String> {
        return (1..21).asFlow()
            .transform {
                when {
                    it % 15 == 0 -> {
                        emit(it.toString())
                        emit("FizzBuzz")
                    }
                    it % 5 == 0 -> {
                        emit(it.toString())
                        emit("Buzz")
                    }
                    it % 3 == 0 -> {
                        emit(it.toString())
                        emit("Fizz")
                    }
                    (it % 3 != 0 && it % 5 != 0 && it % 15 != 0) -> {
                        emit(it.toString())
                    }
                }
            }
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> {
        val flow1 = flowOf("Red", "Green", "Blue", "Black", "White")
        val flow2 = flowOf("Circle", "Square", "Triangle")
        return flow1.zip(flow2){f1, f2 -> Pair(f1, f2)}
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return (1..10).asFlow()
            .transform {
                if (it == 5) {
                    throw IllegalArgumentException("Failed")
                    // throw SecurityException("Security breach") - для теста `test task4 negative`
                } else {
                    emit(it)
                }
            }.catch {
                when (it) {
                    is IllegalArgumentException -> {
                        sampleRepository.completed()
                        emit(-1)
                    }
                    !is IllegalArgumentException -> {
                        sampleRepository.completed()
                        throw it
                    }
                }
            }
    }
}