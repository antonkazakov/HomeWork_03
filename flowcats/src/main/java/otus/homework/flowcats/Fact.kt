package otus.homework.flowcats

import com.google.gson.annotations.SerializedName

data class Fact(
	@field:SerializedName("fact") var fact: String
)