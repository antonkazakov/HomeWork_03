package otus.homework.flowcats.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель факта о кошке
 *
 * @property text описание факта
 */
// https://alexwohlbruck.github.io/cat-facts/docs/endpoints/facts.html
data class Fact(
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("deleted")
    val deleted: Boolean,
    @field:SerializedName("_id")
    val id: String,
    @field:SerializedName("text")
    val text: String,
    @field:SerializedName("source")
    val source: String,
    @field:SerializedName("used")
    val used: Boolean,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("user")
    val user: String,
    @field:SerializedName("updatedAt")
    val updatedAt: String
)