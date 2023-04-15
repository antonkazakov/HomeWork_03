package otus.homework.flowcats

class CatsNetwork (private val service: CatsService): BaseNetwork(){

    suspend fun getCatFact() =
        getResult {
            service.getCatFact()
        }

}