package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.DetailedFilmEntity
import javax.inject.Inject

class GetDetailedFilmInfoUseCase @Inject constructor(
    private val repository: FilmsRepository
) {

    suspend fun getFilmDetailedDescription(filmId: Int): DetailedFilmEntity {
        return repository.getFilmDetailedDescription(filmId)
    }
}