package otus.homework.flowcats.data

import com.google.gson.annotations.SerializedName

data class Book (
    @field:SerializedName("status") val status: String,
    @field:SerializedName("code") val code: Int,
    @field:SerializedName("total") val total: Int,
    @field:SerializedName("data") val data: List<Data>

)

data class Data (
    @field:SerializedName("title") val title : String,
    @field:SerializedName("author") val author : String,
    @field:SerializedName("genre") val genre : String,
    @field:SerializedName("description") val description : String,
    @field:SerializedName("isbn") val isbn : String,
    @field:SerializedName("image") val image : String,
    @field:SerializedName("published") val published : String,
    @field:SerializedName("publisher") val publisher : String
)