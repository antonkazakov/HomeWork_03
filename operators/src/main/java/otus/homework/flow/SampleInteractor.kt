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
     * 2) убирает чила <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    fun task1(): Flow<String> {
        var count : Int = 0
        return flow {
            sampleRepository.produceNumbers().collect() {
                val n = it * 5
                if ((n > 20) and ((n % 2) != 0) and (count < 3)) {
                    emit(n.toString() + " won")
                    ++count
                }
            }
        }
    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    fun task2(): Flow<String> {
        return flow {
            sampleRepository.produceNumbers().collect() {
                emit(it.toString())
                if ((it % 15) == 0) {
                    emit("FizzBuzz")
                } else if ((it % 5) == 0) {
                    emit("Buzz")
                } else if ((it % 3) == 0) {
                    emit("Fizz")
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
        var last1 : String = ""
        var last2 : String = ""
        val f1 = sampleRepository.produceColors()
        val f2 = sampleRepository.produceForms()
        return combineTransform(f1, f2) { s1, s2 ->
            if ((s1 != last1) and (s2 != last2)) {
                last1 = s1
                last2 = s2
                emit(Pair(s1, s2))
            }
        }
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
                sampleRepository.produceNumbers().collect() {
                    emit(it)
                }
            }
            catch (e: Throwable) {
                 if (e !is IllegalArgumentException) throw e
                emit(-1)
            }
            sampleRepository.completed()
        }
    }
}