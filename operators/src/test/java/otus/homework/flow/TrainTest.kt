package otus.homework.flow

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test


@ExperimentalCoroutinesApi
class TrainTest {

    val dotsRepository = mockk<SampleRepository>(relaxed = true)
    val dotsInteractor = SampleInteractor(dotsRepository)

    @Test
    fun `test task1`() = runBlockingTest {
        every { dotsRepository.produceNumbers() } returns flowOf(7, 12, 4, 8, 11, 5, 7, 16, 99, 1)

        dotsRepository.produceNumbers().collect {

            println(it)
        }



    }


}