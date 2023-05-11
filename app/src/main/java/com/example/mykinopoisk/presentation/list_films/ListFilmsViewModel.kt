package com.example.mykinopoisk.presentation.list_films

import android.app.Application
import androidx.lifecycle.*
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.example.mykinopoisk.domain.usecases.AddOrRemoveFavoriteFilmsUseCase
import com.example.mykinopoisk.domain.usecases.GetTopFilmsUseCase
import com.example.mykinopoisk.domain.usecases.SearchFilmsUseCase
import kotlinx.coroutines.launch

class ListFilmsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmsRepositoryImpl(application)
    private val getTopFilmsUseCase = GetTopFilmsUseCase(repository)
    private val addOrRemoveFavoriteFilmsUseCase = AddOrRemoveFavoriteFilmsUseCase(repository)
    private val searchFilmsUseCase = SearchFilmsUseCase(repository)

    private var page = 1

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing
    var listTopFilms = getTopFilmsUseCase.getTopFilms()
    val listOfFavorites = getTopFilmsUseCase.loadFilmFavorites()
    private val _searchTopFilms = MutableLiveData<MutableList<TopFilmsEntity>>()
    val searchTopFilms: LiveData<MutableList<TopFilmsEntity>>
        get() = _searchTopFilms

    private val _searchFavoriteFilms = MutableLiveData<MutableList<TopFilmsEntity>>()
    val searchFavoriteFilms: LiveData<MutableList<TopFilmsEntity>>
        get() = _searchFavoriteFilms

    private val _errorResponseMessage = MutableLiveData<String>()
    val errorResponseMessage: LiveData<String>
        get() = _errorResponseMessage

    init {
        refreshFilms()
    }

    fun loadFilms() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                getTopFilmsUseCase.loadFilmsList(page)
                page++
            } catch (exception: Exception) {
                _errorResponseMessage.postValue(exception.message)
            }
            _isRefreshing.value = false
        }
    }

    fun refreshFilms() {
        page = 1
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

    fun searchTopFilms(mask:String){
        _isRefreshing.value = true
        viewModelScope.launch {
            _searchTopFilms.value = searchFilmsUseCase.searchFilmsPopular(mask)
            _isRefreshing.value = false
        }
    }

    fun searchFavoriteFilms(mask:String){
        _isRefreshing.value = true
        viewModelScope.launch {
            _searchFavoriteFilms.value = searchFilmsUseCase.searchFilmsFavorites(mask)
            _isRefreshing.value = false
        }
    }
}