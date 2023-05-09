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

    private val _errorResponseMessage = MutableLiveData<String>()
    val errorResponseMessage: LiveData<String>
        get() = _errorResponseMessage

    init {
        refreshFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            try {
                getTopFilmsUseCase.loadFilmsList(page)
                page++
                Log.d(
                    "TOP_FILM",
                    "Data loaded. Page = $page, size = ${listTopFilmsItems.value?.size}"
                )
            } catch (exception: Exception) {
                _errorResponseMessage.postValue(exception.message)
            }
            _isRefreshing.value = false
        }
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
}