package otus.homework.flowcats.repository

import kotlinx.coroutines.flow.Flow
import otus.homework.flowcats.network.model.FactResponse
import otus.homework.flowcats.ui.model.Fact

interface ICatRepository {
	fun listenForCatFacts(): Flow<Fact>

	fun FactResponse.toFact() = Fact(text)
}