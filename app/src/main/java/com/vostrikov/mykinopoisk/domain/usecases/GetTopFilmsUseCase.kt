package com.vostrikov.mykinopoisk.domain.usecases

import androidx.lifecycle.LiveData
import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.TopFilmsEntity
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(
    private val repository: FilmsRepository
) {

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