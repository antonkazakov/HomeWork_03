package otus.homework.flowcats.presentation.statein

import otus.homework.flowcats.domain.Cat

/**
 * Модель состояния с информацией о кошке
 */
sealed class CatUiState {

    /** Состояние бездействия */
    object Idle : CatUiState()

    /**
     * Состояние наличия информации о кошке
     *
     * @property cat информация о кошке
     */
    data class Success(val cat: Cat) : CatUiState()

    /**
     * Состояние отсутствия данных о кошке
     *
     * @property message описание причины отсутствия
     */
    data class Error(val message: String) : CatUiState()
}