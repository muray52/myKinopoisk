package com.example.mykinopoisk.domain.usecases

import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class GetTopFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity> {
        return repository.loadFilmsList(page)
    }
}