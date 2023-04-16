package com.example.mykinopoisk.data.api.model

import com.google.gson.annotations.SerializedName

data class TopFilmsPagesApiModel(
    @SerializedName("pagesCount") var pagesCount: Int? = null,
    @SerializedName("films") var films: ArrayList<TopFilmsApiModel> = arrayListOf()

)