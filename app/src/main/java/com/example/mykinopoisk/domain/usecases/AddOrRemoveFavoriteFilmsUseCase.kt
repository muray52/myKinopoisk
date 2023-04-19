package com.example.mykinopoisk.domain.usecases

import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class AddOrRemoveFavoriteFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun addFilmToFavorite(film: TopFilmsEntity){
        repository.addToFavorites(film)
    }

    suspend fun removeFilmFromFavorite(filmId: Int){
        repository.removeFromFavorites(filmId)
    }
}