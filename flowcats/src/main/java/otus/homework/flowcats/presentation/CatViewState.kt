package otus.homework.flowcats.presentation

import otus.homework.flowcats.domain.CatModel

sealed class CatViewState {
    data class CatFact(val fact: CatModel = CatModel()): CatViewState()
    data class CatError(val errorMessage: String): CatViewState()
}