package com.example.mykinopoisk.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mykinopoisk.data.api.ApiFactory
import com.example.mykinopoisk.data.api.model.TopFilmsPagesApiModel
import com.example.mykinopoisk.data.db.AppDatabase
import com.example.mykinopoisk.data.mapper.FilmMapper
import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import androidx.lifecycle.Transformations

class FilmsRepositoryImpl(application: Application) : FilmsRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = FilmMapper()
    private val filmsDao = AppDatabase.getInstance(application).filmsDao()

    override suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity> =
        mapper.mapFilmPagesToTopFilmsEntity(apiService.getTopRateFilms(page = page))

    override suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        val detailedFilmEntity = apiService.getFilmDetailedDescription(filmId)
        return mapper.mapFilmDescriptionApiToDetailedFilmEntity(detailedFilmEntity, filmId)
    }

    override suspend fun addToFavorites(film: TopFilmsEntity) {
        Log.d("TEST_DB", "add_filmId = ${film.filmId}")
        filmsDao.insert(mapper.mapFilmsEntityToFavoritesFilm(film))
    }

    override suspend fun removeFromFavorites(filmId: Int) {
        Log.d("TEST_DB", "delete_filmId = ${filmId}")
        filmsDao.deleteById(filmId)
    }

    override fun getFavorites(): LiveData<MutableList<TopFilmsEntity>> {
        return liveData {
            emit(mapper.mapFavoritesFilmToFilmsEntity(filmsDao.getAll()))
        }
    }
}