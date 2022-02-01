package otus.homework.flowcats.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.ui.model.Fact
import kotlin.random.Random

class CatRepositoryMock : ICatRepository {

	private var counter = 0

	override fun listenForCatFacts(): Flow<Fact> = flow {
		while (true) {
			counter++
			emit(createRandomFact())
			delay(1000)
			if(counter==15) throw Throwable("test error")
		}
	}

	private fun createRandomFact(): Fact {
		val number = Random.nextInt(0, 100)
		return Fact("факт №$number")
	}
}