package com.example.mykinopoisk.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mykinopoisk.data.api.ApiFactory
import com.example.mykinopoisk.data.api.model.FilmDescriptionApiModel
import com.example.mykinopoisk.data.db.AppDatabase
import com.example.mykinopoisk.data.mapper.FilmMapper
import com.example.mykinopoisk.data.sharedpreferences.SharedPref
import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.model.LoginEntity
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.example.mykinopoisk.util.BaseRepo

class FilmsRepositoryImpl(application: Application) : FilmsRepository, BaseRepo() {

    private val apiService = ApiFactory.apiService
    private val mapper = FilmMapper()
    private val filmsDao = AppDatabase.getInstance(application).filmsDao()
    private val sharedPref = SharedPref(application)

    override suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity> =
        mapper.mapFilmPagesToTopFilmsEntity(
            apiService.getTopRateFilms(
                page = page,
                apiKey = sharedPref.getApiKey()
            )
        )

    override suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        val response =
            safeApiCall{apiService.getFilmDetailedDescription(sharedPref.getApiKey(), filmId)}
        return if(response.body != null) {
            mapper.mapFilmDescriptionApiToDetailedFilmEntity(response.body, filmId)
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun addToFavorites(film: TopFilmsEntity) {
        Log.d("TEST_DB", "add_filmId = ${film.filmId}")
        filmsDao.insertFavorites(mapper.mapFilmsEntityToFavoritesFilm(film))
    }

    override suspend fun removeFromFavorites(filmId: Int) {
        Log.d("TEST_DB", "delete_filmId = $filmId")
        filmsDao.deleteFavoritesById(filmId)
    }

    override fun getFavorites(): LiveData<MutableList<TopFilmsEntity>> =
        Transformations.map(filmsDao.getFavoritesAll()) {
            mapper.mapFavoritesFilmToFilmsEntity(it)
        }

    override suspend fun reloadFavorites() {
        val filmFavoritesIDs: List<Int> = filmsDao.getFavoritesAllNoLiveData().map {
            it.filmId
        }
        val filmFavoritesList = mutableListOf<FilmDescriptionApiModel>()
        filmFavoritesIDs.forEach {
            apiService.getFilmDetailedDescription(sharedPref.getApiKey(), it)?.body()?.let { it1 ->
                filmFavoritesList.add(
                    it1
                )
            }
        }
        if (filmFavoritesList.size > 0) {
            filmsDao.deleteFavoritesAll()
            mapper.mapFilmDescriptionApiListToFavoritesFilmList(filmFavoritesList).forEach {
                filmsDao.insertFavorites(it)
            }
        }
    }

    override fun getTopFilms(): LiveData<MutableList<TopFilmsEntity>> =
        Transformations.map(filmsDao.getTopFilmsAll()) {
            mapper.mapTopFilmsDbToFilmsEntity(it)
        }

    override suspend fun insertTopFilms(topFilms: MutableList<TopFilmsEntity>) {
        filmsDao.insertTopFilms(mapper.mapListOfFilmsEntityToListOfTopFilmsDb(topFilms))
    }

    override suspend fun deleteTopFilms() {
        filmsDao.deleteTopFilmsAll()
    }

    override suspend fun deleteTopFilmsById(filmId: Int) {
        filmsDao.deleteTopFilmsById(filmId)
    }

    override suspend fun updateTopFilms(filmId: Int, favoritesFlag: Boolean) {
        filmsDao.updateTopFilms(filmId, favoritesFlag)
    }

    override fun signInGuest(): Boolean {
        sharedPref.writeGuestApiKey()
        return sharedPref.getApiKey() != ""
    }


    override suspend fun signIn(login: LoginEntity): Boolean {
        val postResponse = safeApiCall{apiService.postAuth(
            mapper.mapLoginEntityToLoginRequestApiModel(login))}

        val bearer = postResponse.headers?.get("authorization")
        val response = apiService.getXApiKey(bearer ?: "")

        sharedPref.deleteApiKey()
        response.body()?.token?.let {
            sharedPref.writeApiKey(it)
        }
        return sharedPref.getApiKey() != ""
    }
}