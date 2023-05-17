package com.example.mykinopoisk.data.api.model

import com.google.gson.annotations.SerializedName

data class LoginRequestApiModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
