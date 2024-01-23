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
        return flowOf("1","2","3","4","5","6","7","8","9","0")
            .map { it.toInt() * 5 }
            .filter { it > 20 && it%2 == 0}
            .map { it.toString() + " won" }
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
        return flowOf("11", "2", "3", "40", "5", "6", "70", "8", "9", "0")
            .transform { it ->
                if (it.toInt() % 3 == 0) {
                    emit(it + " Fizz")
                } else if (it.toInt() % 5 == 0) {
                    emit(it + " Buzz")
                } else if (it.toInt() % 15 == 0) {
                    emit(it + " FizzBuzz")
                } else {
                    emit(it)
                }
            }
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> {
        val list1 = listOf("1","2","3","4","5","6","7","8","9","0").asFlow()
        val list2 = listOf("37", "41", "25", "1").asFlow()
        return list1.zip(list2) { it1, it2 -> Pair(it1, it2) }
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return flowOf(0).flatMapLatest { flow<Int> { throw IllegalArgumentException(it.toString())}}
            .catch{ e -> if(e is IllegalArgumentException) emit(-1) else throw e}
            .onCompletion { sampleRepository.completed() }
    }
}