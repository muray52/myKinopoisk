package com.example.mykinopoisk.domain.usecases

import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class SearchFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun searchFilmsPopular(mask: String): MutableList<TopFilmsEntity> {
        return repository.searchFilmsPopular(mask)
    }

    suspend fun searchFilmsFavorites(mask: String): MutableList<TopFilmsEntity> {
        return repository.searchFilmsFavorites(mask)
    }
}