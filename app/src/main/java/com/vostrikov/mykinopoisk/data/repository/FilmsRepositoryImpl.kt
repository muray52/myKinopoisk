package com.vostrikov.mykinopoisk.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vostrikov.mykinopoisk.data.api.ApiService
import com.vostrikov.mykinopoisk.data.api.model.FilmDescriptionApiModel
import com.vostrikov.mykinopoisk.data.db.FilmsDao
import com.vostrikov.mykinopoisk.data.db.model.FavoritesFilmDbModel
import com.vostrikov.mykinopoisk.data.db.model.TopFilmsDbModel
import com.vostrikov.mykinopoisk.data.mapper.FilmMapper
import com.vostrikov.mykinopoisk.data.sharedpreferences.SharedPref
import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.DetailedFilmEntity
import com.vostrikov.mykinopoisk.domain.model.LoginEntity
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity
import com.vostrikov.mykinopoisk.util.BaseRepo
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: FilmMapper,
    private val filmsDao: FilmsDao,
    private val sharedPref: SharedPref
) : FilmsRepository, BaseRepo() {

    override suspend fun loadFilmsList(page: Int) {
        val response = safeApiCall {
            apiService.getTopRateFilms(
                page = page,
                apiKey = sharedPref.getApiKey()
            )
        }
        if (response.body != null) {
            val listOfFavorites = filmsDao.getFavoritesAllNoLiveData()
            val listOfFilms = mapper.mapFilmPagesToTopFilmsDbModel(response.body)
            changeFavoriteFlagStatus(listOfFilms, listOfFavorites)
            filmsDao.insertTopFilms(listOfFilms)
            Log.d(
                "REPOSITORY",
                "Film data loaded. Page = $page has been loaded"
            )
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        val response =
            safeApiCall { apiService.getFilmDetailedDescription(sharedPref.getApiKey(), filmId) }
        return if (response.body != null) {
            mapper.mapFilmDescriptionApiToDetailedFilmEntity(response.body, filmId)
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun addToFavorites(film: TopFilmsEntity) {
        Log.d("REPOSITORY", "The film has been added, add_filmId = ${film.filmId}")
        filmsDao.insertFavorites(mapper.mapFilmsEntityToFavoritesFilm(film))
    }

    override suspend fun removeFromFavorites(filmId: Int) {
        Log.d("REPOSITORY", "The film has been deleted, delete_filmId = $filmId")
        filmsDao.deleteFavoritesById(filmId)
    }

    override fun getFavorites(): LiveData<MutableList<TopFilmsEntity>> =
        Transformations.map(filmsDao.getFavoritesAll()) {
            mapper.mapFavoritesFilmDbToFilmsEntity(it)
        }

    override suspend fun reloadFavorites() {
        val filmFavoritesIDs: List<Int> = filmsDao.getFavoritesAllNoLiveData().map {
            it.filmId
        }
        val filmFavoritesList = mutableListOf<FilmDescriptionApiModel>()
        filmFavoritesIDs.forEach {
            apiService.getFilmDetailedDescription(sharedPref.getApiKey(), it).body()?.let { it1 ->
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
        val postResponse = safeApiCall {
            apiService.postAuth(
                mapper.mapLoginEntityToLoginRequestApiModel(login)
            )
        }

        val bearer = postResponse.headers?.get("authorization")
        val response = apiService.getXApiKey(bearer ?: "")

        sharedPref.deleteApiKey()
        response.body()?.token?.let {
            sharedPref.writeApiKey(it)
        }
        return sharedPref.getApiKey() != ""
    }

    private fun changeFavoriteFlagStatus(
        listOfFilms: List<TopFilmsDbModel>,
        listOfFavorites: List<FavoritesFilmDbModel>
    ) {
        listOfFilms.forEach { it_film ->
            it_film.favoritesFlag =
                (listOfFavorites.filter { it.filmId == it_film.filmId }.size == 1)
        }
    }

    override suspend fun searchFilmsPopular(mask: String): MutableList<TopFilmsEntity> =
        mapper.mapTopFilmsDbToFilmsEntity(filmsDao.searchTopFilmsByMask(mask))

    override suspend fun searchFilmsFavorites(mask: String): MutableList<TopFilmsEntity> =
        mapper.mapFavoritesFilmDbToFilmsEntity(filmsDao.searchFavoriteFilmsByMask(mask))


}