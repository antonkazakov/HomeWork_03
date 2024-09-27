package otus.homework.flowcats

import otus.homework.flowcats.data.Book
import retrofit2.http.GET

interface BookService {

    @GET("books?_quantity=1")
    suspend fun getBook(): Book
}