package com.example.mykinopoisk.data.repository

import com.example.mykinopoisk.data.api.ApiFactory
import com.example.mykinopoisk.data.api.model.TopFilmsApiModel
import com.example.mykinopoisk.data.api.model.TopFilmsPagesApiModel
import com.example.mykinopoisk.data.mapper.FilmMapper
import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class FilmsRepositoryImpl : FilmsRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = FilmMapper()

    override suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity> {
        val topFilmsApi: TopFilmsPagesApiModel = apiService.getTopRateFilms(page = page)
        return mapper.mapFilmPagesToTopFilmsEntity(topFilmsApi)
    }

    override suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        val detailedFilmEntity = apiService.getFilmDetailedDescription(filmId)
        return mapper.mapFilmDescriptionApiToDetailedFilmEntity(detailedFilmEntity, filmId)
    }
}