package com.example.mykinopoisk.domain

import androidx.lifecycle.LiveData
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class GetTopFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity> {
        return repository.loadFilmsList(page)
    }
}