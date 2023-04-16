package com.example.mykinopoisk.domain

import androidx.lifecycle.LiveData
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.model.TopFilmsEntity

interface FilmsRepository {

    suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity>

    suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity

}