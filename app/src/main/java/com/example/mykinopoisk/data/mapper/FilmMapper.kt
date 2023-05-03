package com.example.mykinopoisk.data.mapper

import com.example.mykinopoisk.data.api.model.*
import com.example.mykinopoisk.data.db.model.FavoritesFilmDbModel
import com.example.mykinopoisk.data.db.model.TopFilmsDbModel
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.model.LoginEntity
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class FilmMapper {

    fun mapFilmPagesToTopFilmsEntity(filmPages: TopFilmsPagesApiModel): MutableList<TopFilmsEntity> {
        val topFilmsEntity: MutableList<TopFilmsEntity> = mutableListOf()

        val topFilms = filmPages.films
        topFilms.forEach {

            topFilmsEntity.add(
                TopFilmsEntity(
                    0,
                    it.filmId,
                    it.nameRu,
                    it.year,
                    it.countries,
                    it.genres,
                    createGenreAndYear(it.genres, it.year ?: ""),
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

    fun mapFilmsEntityToFavoritesFilm(film: TopFilmsEntity): FavoritesFilmDbModel {
        return FavoritesFilmDbModel(
            film.filmId,
            film.nameRu,
            film.year,
            film.genreAndYear,
            film.posterUrlPreview
        )
    }

    fun mapFavoritesFilmToFilmsEntity(
        film: MutableList<FavoritesFilmDbModel>
    ): MutableList<TopFilmsEntity> {

        val listOfFavorites: MutableList<TopFilmsEntity> = mutableListOf()
        film.forEach {
            listOfFavorites.add(
                TopFilmsEntity(
                    0,
                    it.filmId,
                    it.nameRu,
                    it.year,
                    arrayListOf(),
                    arrayListOf(),
                    it.genreAndYear,
                    null,
                    it.posterUrlPreview,
                    true
                )
            )
        }
        return listOfFavorites
    }

    fun mapFilmDescriptionApiListToFavoritesFilmList(
        filmsDetailed: MutableList<FilmDescriptionApiModel>
    ): MutableList<FavoritesFilmDbModel> {

        val listOfFavorites: MutableList<FavoritesFilmDbModel> = mutableListOf()
        filmsDetailed.forEach {
            listOfFavorites.add(
                FavoritesFilmDbModel(
                    it.kinopoiskId ?: 0,
                    it.nameRu,
                    it.year.toString(),
                    createGenreAndYear(it.genres,it.year.toString()),
                    it.posterUrlPreview
                )
            )
        }
        return listOfFavorites
    }

    fun mapTopFilmsDbToFilmsEntity(
        film: MutableList<TopFilmsDbModel>
    ): MutableList<TopFilmsEntity> {

        val listOfFavorites: MutableList<TopFilmsEntity> = mutableListOf()
        film.forEach {
            listOfFavorites.add(
                TopFilmsEntity(
                    it.id,
                    it.filmId,
                    it.nameRu,
                    it.year,
                    arrayListOf(),
                    arrayListOf(),
                    it.genreAndYear,
                    null,
                    it.posterUrlPreview,
                    it.favoritesFlag
                )
            )
        }
        return listOfFavorites
    }

    fun mapListOfFilmsEntityToListOfTopFilmsDb(topFlims: MutableList<TopFilmsEntity>): List<TopFilmsDbModel> {
        val listOfTopFilms: MutableList<TopFilmsDbModel> = mutableListOf()
        topFlims.forEach {
            listOfTopFilms.add(
                TopFilmsDbModel(
                    it.id,
                    it.filmId,
                    it.nameRu,
                    it.year,
                    createGenreAndYear(it.genres, it.year?:""),
                    it.posterUrlPreview,
                    it.favoritesFlag
                )
            )
        }
        return listOfTopFilms
    }

    fun mapLoginEntityToLoginRequestApiModel(login: LoginEntity) = LoginRequestApiModel(
        login.email,
        login.password
    )

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

    private fun createGenreAndYear(genresArray: ArrayList<GenresApiModel>, year: String): String {
        val mainGenre =
            genresArray[0].genre?.replaceFirstChar { it_genre -> it_genre.uppercase() }
        return mainGenre + "(" + year + ")"
    }
}