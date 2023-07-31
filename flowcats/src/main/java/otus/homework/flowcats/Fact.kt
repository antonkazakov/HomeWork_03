package otus.homework.flowcats

import com.google.gson.annotations.SerializedName

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
){
	companion object{
		private const val EMPTY_STRING = ""
		fun emptyFact() = Fact(
			createdAt = EMPTY_STRING,
			deleted = false,
			id = EMPTY_STRING,
			text = EMPTY_STRING,
			source = EMPTY_STRING,
			used = false,
			type = EMPTY_STRING,
			user = EMPTY_STRING,
			updatedAt = EMPTY_STRING
		)

	}
}
