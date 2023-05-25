package com.vostrikov.mykinopoisk.domain

import androidx.lifecycle.LiveData
import com.vostrikov.mykinopoisk.domain.model.DetailedFilmEntity
import com.vostrikov.mykinopoisk.domain.model.LoginEntity
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity

interface FilmsRepository {

    suspend fun loadFilmsList(page: Int)

    suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity

    suspend fun addToFavorites(film: TopFilmsEntity)

    suspend fun removeFromFavorites(filmId: Int)

    fun getFavorites(): LiveData<MutableList<TopFilmsEntity>>

    suspend fun reloadFavorites()

    fun getTopFilms(): LiveData<MutableList<TopFilmsEntity>>

    suspend fun insertTopFilms(topFilms: MutableList<TopFilmsEntity>)

    suspend fun deleteTopFilms()

    suspend fun deleteTopFilmsById(filmId: Int)

    suspend fun updateTopFilms(filmId: Int, favoritesFlag: Boolean)

    fun signInGuest(): Boolean

    suspend fun signIn(login: LoginEntity): Boolean

    suspend fun searchFilmsPopular(mask: String): MutableList<TopFilmsEntity>

    suspend fun searchFilmsFavorites(mask: String): MutableList<TopFilmsEntity>
}