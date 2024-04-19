package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip

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
            .map { it * 5 }            // Умножаем каждое число на 5
            .filter { it > 20 }        // Оставляем числа больше 20
            .filter { it % 2 != 0 }    // Убираем четные числа
            .map { "$it won" }         // Добавляем постфикс "won"
            .take(3)             // Берем первые 3 элемента
    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    fun task2(): Flow<String> {
        return sampleRepository.produceNumbers()
            .transform { value ->
                emit(value.toString())          // эмитим само число
                when {
                    value % 15 == 0 -> emit("FizzBuzz") // после него эмитим строку FizzBuzz
                    value % 5 == 0 -> emit("Buzz")      // после него эмитим строку Buzz
                    value % 3 == 0 -> emit("Fizz")      // после него эмитим строку Fizz
                }
            }
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> {
        return sampleRepository.produceColors()
            .zip(sampleRepository.produceForms()) { f1, f2 -> Pair(f1, f2) }
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return sampleRepository.produceNumbers().catch { e ->
            if (e is IllegalArgumentException) {
                emit(-1)
            } else {
                throw e
            }
        }.onCompletion {
            sampleRepository.completed()
        }
    }
}