package otus.homework.flowcats

sealed class State<out r> {

    data class success<out t>(val data: t) : State<t>()
    data class error(val exception: Exception) : State<Nothing>()
    data class loading(val  mes: String) : State<Nothing>()
    companion object {
        inline fun <T> on(f: () -> T): State<T> = try {
            success(f())
        } catch (ex: Exception) {
            error(ex)
        }
    }
}