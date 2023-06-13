package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity
import javax.inject.Inject

class AddOrRemoveFavoriteFilmsUseCase @Inject constructor(
    private val repository: FilmsRepository
) {

    suspend fun addFilmToFavorite(film: TopFilmsEntity) {
        repository.addToFavorites(film)
        film.favoritesFlag = true
    }

    suspend fun removeFilmFromFavorite(filmId: Int) {
        repository.removeFromFavorites(filmId)
    }
}