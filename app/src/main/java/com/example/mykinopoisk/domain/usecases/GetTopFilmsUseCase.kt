package com.example.mykinopoisk.domain.usecases

import androidx.lifecycle.LiveData
import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class GetTopFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun loadFilmsList(page: Int): MutableList<TopFilmsEntity> {
        return repository.loadFilmsList(page)
    }

    fun loadFilmFavorites(): LiveData<MutableList<TopFilmsEntity>> {
        return repository.getFavorites()
    }
}