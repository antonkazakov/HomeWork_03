package otus.homework.flow

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
class SampleInteractor(
    private val sampleRepository: SampleRepository,
) {

    fun main() = runBlocking {
        val flow1 = task1()
        flow1.collect {
            Log.d("TASK_1", it)
        }

        val flow2 = task2()
        flow2.collect {
            Log.d("TASK_2", it)
        }

        val flow3 = task3()
        flow3.collect {
            Log.d("TASK_3", "${it.first} — ${it.second}")
        }

        val flow4 = task4()
        flow4.collect {
            Log.d("TASK_4", "$it")
        }
    }

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
            .map { it * 5 }
            .filter { it > 20 && (it % 2) != 0 }
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
        return sampleRepository.produceNumbers()
            .transform {
                emit("$it")
                var str = ""
                if (it % 3 == 0) {
                    str += "Fizz"
                }
                if (it % 5 == 0) {
                    str += "Buzz"
                }
                if (str.isNotBlank()) {
                    emit(str)
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
            .zip(sampleRepository.produceForms()){
                it1, it2 -> Pair(it1, it2)
            }
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return sampleRepository.produceNumbers()
            // Для тестов
            .onEach { if (it < 10) throw java.lang.IllegalArgumentException()
            else if (it > 40) throw java.lang.Exception()}

            .catch {
                when(it){
                    is java.lang.IllegalArgumentException -> emit(-1)
                    else -> throw(it)
                }
            }
            .onCompletion { sampleRepository.completed() }
    }
}