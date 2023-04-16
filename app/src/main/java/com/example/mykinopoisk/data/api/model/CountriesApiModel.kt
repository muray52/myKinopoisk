package com.example.mykinopoisk.data.api.model

import com.google.gson.annotations.SerializedName

data class CountriesApiModel (
    @SerializedName("country" ) var country : String? = null
)