package com.example.mykinopoisk.domain.model

import com.example.mykinopoisk.data.api.model.CountriesApiModel
import com.example.mykinopoisk.data.api.model.GenresApiModel


data class TopFilmsEntity (
    val id: Int,
    val filmId: Int,
    val nameRu: String?,
    val year: String?,
    var countries: ArrayList<CountriesApiModel> = arrayListOf(),
    var genres: ArrayList<GenresApiModel> = arrayListOf(),
    var genreAndYear: String?,
    var posterUrl: String?,
    var posterUrlPreview: String?,
    var favoritesFlag: Boolean
)