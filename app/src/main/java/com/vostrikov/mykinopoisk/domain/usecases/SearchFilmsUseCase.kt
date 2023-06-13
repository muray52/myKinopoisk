package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity
import javax.inject.Inject

class SearchFilmsUseCase @Inject constructor(
    private val repository: FilmsRepository
) {

    suspend fun searchFilmsPopular(mask: String): MutableList<TopFilmsEntity> {
        return repository.searchFilmsPopular(mask)
    }

    suspend fun searchFilmsFavorites(mask: String): MutableList<TopFilmsEntity> {
        return repository.searchFilmsFavorites(mask)
    }
}