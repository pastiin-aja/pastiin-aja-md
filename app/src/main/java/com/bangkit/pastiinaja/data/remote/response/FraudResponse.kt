package com.bangkit.pastiinaja.data.remote.response

import com.google.gson.annotations.SerializedName

data class FraudResponse(

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	@field:SerializedName("data")
	val data: List<FraudItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FraudPostResponse(

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	@field:SerializedName("data")
	val data: FraudItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FraudItem(

	@field:SerializedName("result")
	val result: Any? = null,

	@field:SerializedName("image_link")
	val imageLink: Any? = null,

	@field:SerializedName("fraud_id")
	val fraudId: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("text_input")
	val textInput: String? = null,

	@field:SerializedName("is_shared")
	val isShared: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null
)
