package com.example.mykinopoisk.domain.model

import com.example.mykinopoisk.data.api.model.CountriesApiModel
import com.example.mykinopoisk.data.api.model.GenresApiModel


data class DetailedFilmEntity (
    val filmId: Int?,
    val nameRu: String?,
    val year: String?,
    var countries: ArrayList<CountriesApiModel> = arrayListOf(),
    var genres: ArrayList<GenresApiModel> = arrayListOf(),
    var genresString: String?,
    var countriesString: String?,
    var description: String?,
    var posterUrl: String?,
    var favoritesFlag: Boolean
)