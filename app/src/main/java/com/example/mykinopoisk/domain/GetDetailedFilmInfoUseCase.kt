package com.example.mykinopoisk.domain

import com.example.mykinopoisk.domain.model.DetailedFilmEntity

class GetDetailedFilmInfoUseCase(private val repository: FilmsRepository) {

    suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        return repository.getFilmDetailedDescription(filmId)
    }
}