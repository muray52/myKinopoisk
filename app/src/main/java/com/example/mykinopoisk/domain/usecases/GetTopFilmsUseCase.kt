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

    suspend fun reloadFilmFavorites() {
        return repository.reloadFavorites()
    }

    fun getTopFilms(): LiveData<MutableList<TopFilmsEntity>> {
        return repository.getTopFilms()
    }

    suspend fun insertTopFilms(topFilms: MutableList<TopFilmsEntity>) {
        return repository.insertTopFilms(topFilms)
    }

    suspend fun deleteTopFilms() {
        repository.deleteTopFilms()
    }

    suspend fun deleteTopFilmsById(filmId: Int) {
        return repository.deleteTopFilmsById(filmId)
    }

    suspend fun updateTopFilms(topFilm: TopFilmsEntity) {
        return repository.updateTopFilms(topFilm)
    }
}