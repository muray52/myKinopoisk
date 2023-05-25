package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity

class SearchFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun searchFilmsPopular(mask: String): MutableList<TopFilmsEntity> {
        return repository.searchFilmsPopular(mask)
    }

    suspend fun searchFilmsFavorites(mask: String): MutableList<TopFilmsEntity> {
        return repository.searchFilmsFavorites(mask)
    }
}