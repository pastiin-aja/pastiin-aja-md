package com.bangkit.pastiinaja.data.remote.response

import com.google.gson.annotations.SerializedName

data class FraudsResponse(

    @field:SerializedName("listFraud")
    val listFraud: List<ListFraudItem> = emptyList(),

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ListFraudItem(

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("uploadedAt")
    val uploadedAt: String? = null,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("id")
    val id: String? = null,
)