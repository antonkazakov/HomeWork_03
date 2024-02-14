package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    val catsStateFlow = catsRepository.listenForCatFacts()
}

@Suppress("UNCHECKED_CAST")
class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}