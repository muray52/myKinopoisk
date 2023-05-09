package com.example.mykinopoisk.domain.usecases

import androidx.lifecycle.LiveData
import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.TopFilmsEntity

class GetTopFilmsUseCase(private val repository: FilmsRepository) {

    suspend fun loadFilmsList(page: Int) {
        repository.loadFilmsList(page)
    }

    fun loadFilmFavorites(): LiveData<MutableList<TopFilmsEntity>> {
        return repository.getFavorites()
    }

    suspend fun reloadFilmFavorites() {
        return repository.reloadFavorites()
    }

    fun getTopFilms(): LiveData<MutableList<TopFilmsEntity>> {
        return repository.getTopFilms()
    }

    suspend fun deleteTopFilms() {
        repository.deleteTopFilms()
    }

    suspend fun updateTopFilms(filmId: Int, favoritesFlag: Boolean) {
        return repository.updateTopFilms(filmId, favoritesFlag)
    }
}