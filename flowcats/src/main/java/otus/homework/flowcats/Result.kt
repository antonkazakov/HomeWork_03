package otus.homework.flowcats

/*
Создать sealed класс Result. Унаследовать от него классы Success<T>, Error.
 Использовать эти классы как стейт необходимый для рендеринга/отображени ошибки
 */
sealed class Result {

    data class Success<T>(val valueCats: T) : Result()

    data class Error(val error: String) : Result()

    object Init : Result()

}