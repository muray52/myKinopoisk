package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.DetailedFilmEntity

class GetDetailedFilmInfoUseCase(private val repository: FilmsRepository) {

    suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        return repository.getFilmDetailedDescription(filmId)
    }
}