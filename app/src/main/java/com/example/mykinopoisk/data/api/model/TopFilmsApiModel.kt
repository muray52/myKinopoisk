package com.example.mykinopoisk.data.api.model

import com.google.gson.annotations.SerializedName

data class TopFilmsApiModel (
    @SerializedName("filmId"           ) var filmId           : Int                 = 0,
    @SerializedName("nameRu"           ) var nameRu           : String?              = null,
    @SerializedName("nameEn"           ) var nameEn           : String?              = null,
    @SerializedName("year"             ) var year             : String?              = null,
    @SerializedName("filmLength"       ) var filmLength       : String?              = null,
    @SerializedName("countries"        ) var countries        : ArrayList<CountriesApiModel> = arrayListOf(),
    @SerializedName("genres"           ) var genres           : ArrayList<GenresApiModel>    = arrayListOf(),
    @SerializedName("rating"           ) var rating           : String?              = null,
    @SerializedName("ratingVoteCount"  ) var ratingVoteCount  : Int?                 = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?              = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?              = null,
    @SerializedName("ratingChange"     ) var ratingChange     : String?              = null

)