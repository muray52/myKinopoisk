package com.example.mykinopoisk.domain.usecases

import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.DetailedFilmEntity

class GetDetailedFilmInfoUseCase(private val repository: FilmsRepository) {

    suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        return repository.getFilmDetailedDescription(filmId)
    }
}