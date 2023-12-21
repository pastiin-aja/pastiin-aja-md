package com.bangkit.pastiinaja.data.pref

data class FraudTextBody(
    val user_id: String,
    val text_input: String,
    val is_shared: Boolean ?= false
)

data class FraudImageBody(
    val user_id: String,
    val image_base64: String,
    val is_shared: Boolean ?= false
)
