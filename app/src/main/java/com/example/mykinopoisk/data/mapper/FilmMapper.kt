package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.api.model.CountriesApiModel
import com.example.mykinopoisk.data.api.model.FilmDescriptionApiModel
import com.example.mykinopoisk.data.api.model.GenresApiModel
import com.example.mykinopoisk.data.api.model.TopFilmsPagesApiModel
import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class FilmMapper {

    fun mapFilmPagesToTopFilmsEntity(filmPages: TopFilmsPagesApiModel): MutableList<TopFilmsEntity> {
        val topFilmsEntity: MutableList<TopFilmsEntity> = mutableListOf()

        val topFilms = filmPages.films
        topFilms.forEach {

            val genresArray = it.genres
            val mainGenre =
                genresArray[0].genre?.replaceFirstChar { it_genre -> it_genre.uppercase() }
            val genreAndYear = mainGenre + "(" + it.year + ")"
            topFilmsEntity.add(
                TopFilmsEntity(
                    it.filmId,
                    it.nameRu,
                    it.year,
                    it.countries,
                    it.genres,
                    genreAndYear,
                    it.posterUrl,
                    it.posterUrlPreview,
                    false
                )
            )
        }
        return topFilmsEntity
    }


    fun mapFilmDescriptionApiToDetailedFilmEntity(
        detailedFilmApi: FilmDescriptionApiModel, filmId: Int
    ): DetailedFilmEntity {

        val genresString = createGenresString(detailedFilmApi.genres)
        val countriesString = createCountriesString(detailedFilmApi.countries)
        return (
                DetailedFilmEntity(
                    filmId,
                    detailedFilmApi.nameRu,
                    detailedFilmApi.year.toString(),
                    detailedFilmApi.countries,
                    detailedFilmApi.genres,
                    genresString,
                    countriesString,
                    detailedFilmApi.description,
                    detailedFilmApi.posterUrl,
                    false
                )
                )
    }

    private fun createGenresString(genresArray: ArrayList<GenresApiModel>): String {
        var genresString = "Жанры:"
        if (genresArray.size > 1) {
            for (i in 0 until genresArray.size - 1) {
                genresString += " " + genresArray[i].genre + ","
            }
        }
        genresString += " " + genresArray.last().genre
        return genresString
    }

    private fun createCountriesString(countriesArray: ArrayList<CountriesApiModel>): String {
        var countriesString = "Страны:"
        if (countriesArray.size > 1) {
            for (i in 0 until countriesArray.size - 1) {
                countriesString += " " + countriesArray[i].country + ","
            }
        }
        countriesString += " " + countriesArray.last().country
        return countriesString
    }

}