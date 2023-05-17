package com.example.mykinopoisk.data.api.model

import com.google.gson.annotations.SerializedName

data class LoginInfoApiModel(
    @SerializedName("id") var id: Int,
    @SerializedName("email") var email: String,
    @SerializedName("firstName") var firstName: String?,
    @SerializedName("lastName") var lastName: String?,
    @SerializedName("usedQuota") var usedQuota: String?,
    @SerializedName("emailNotification") var emailNotification: Boolean,
    @SerializedName("quota") var quota: Int?,
    @SerializedName("token") var token: String,
    @SerializedName("isActive") var isActive: Boolean
)