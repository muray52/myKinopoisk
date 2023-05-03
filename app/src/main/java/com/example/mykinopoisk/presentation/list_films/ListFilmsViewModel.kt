package com.example.mykinopoisk.presentation.list_films

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.example.mykinopoisk.domain.usecases.AddOrRemoveFavoriteFilmsUseCase
import com.example.mykinopoisk.domain.usecases.GetTopFilmsUseCase
import kotlinx.coroutines.launch

class ListFilmsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmsRepositoryImpl(application)
    private val getTopFilmsUseCase = GetTopFilmsUseCase(repository)
    private val addOrRemoveFavoriteFilmsUseCase = AddOrRemoveFavoriteFilmsUseCase(repository)

    private var page = 1

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing
    val listTopFilmsItems = getTopFilmsUseCase.getTopFilms()
    val listOfFavorites = getTopFilmsUseCase.loadFilmFavorites()

    init {
        refreshFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            val data = getTopFilmsUseCase.loadFilmsList(page)
            changeFavoriteFlagStatus(data)
            getTopFilmsUseCase.insertTopFilms(data)
            _isRefreshing.value = false
            page++
        }
        Log.d("TEST_SCROLL", "page = $page, size = ${listTopFilmsItems.value?.size}")
    }

    fun refreshFilms() {
        page = 1
        _isRefreshing.value = true
        deleteFilmsList()
        loadFilms()
    }

    fun refreshFavoritesFilms() {
        _isRefreshing.value = true
        viewModelScope.launch {
            getTopFilmsUseCase.reloadFilmFavorites()
            _isRefreshing.value = false
        }
    }

    private fun deleteFilmsList() {
        viewModelScope.launch {
            getTopFilmsUseCase.deleteTopFilms()
        }
    }

    fun changeFavoriteState(filmItem: TopFilmsEntity) {
        viewModelScope.launch {
            val newItem = filmItem.copy(favoritesFlag = !filmItem.favoritesFlag)
            getTopFilmsUseCase.updateTopFilms(newItem.filmId, newItem.favoritesFlag)
            if (newItem.favoritesFlag) {
                addOrRemoveFavoriteFilmsUseCase.addFilmToFavorite(newItem)
            } else {
                addOrRemoveFavoriteFilmsUseCase.removeFilmFromFavorite(newItem.filmId)
            }
        }
    }

    private fun changeFavoriteFlagStatus(listOfFilms: MutableList<TopFilmsEntity>) {
        listOfFilms.forEach { it_film ->
            it_film.favoritesFlag =
                (listOfFavorites.value?.filter { it.filmId == it_film.filmId }?.size == 1)
        }
    }
}