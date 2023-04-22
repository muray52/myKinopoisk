package com.example.mykinopoisk.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.TopFilmsEntity
import com.example.mykinopoisk.domain.usecases.AddOrRemoveFavoriteFilmsUseCase
import com.example.mykinopoisk.domain.usecases.GetTopFilmsUseCase
import kotlinx.coroutines.launch

class TopFilmsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmsRepositoryImpl(application)
    private val getTopFilmsUseCase = GetTopFilmsUseCase(repository)
    private val addOrRemoveFavoriteFilmsUseCase = AddOrRemoveFavoriteFilmsUseCase(repository)

    private var page = 1

    val isRefreshing = MutableLiveData<Boolean>()

    val listTopFilmsItems = getTopFilmsUseCase.getTopFilms()
//    val listTopFilmsItems = liveData {
//        isRefreshing.value = true
//        val data = getTopFilmsUseCase.loadFilmsList(page)
//        changeFavoriteFlagStatus(data)
//        emit(data)
//        Log.d("TEST_SCROLL", "page = $page")
//        page++
//        isRefreshing.value = false
//    }

    val listOfFavorites = getTopFilmsUseCase.loadFilmFavorites()

    init {
        loadFilms(true)
    }

//    init {
//        viewModelScope.launch {
//            isRefreshing.value = true
//            listTopFilmsItems.value?.addAll(getTopFilmsUseCase.loadFilmsList(1))
//            page++
//            isRefreshing.value = false
//        }
//    }

    fun loadFilms(refresh: Boolean = false) {
        if (refresh) {
            page = 1
        }
        viewModelScope.launch {
            if (page == 1) {
                isRefreshing.value = true
                getTopFilmsUseCase.deleteTopFilms()
            }
            val data = getTopFilmsUseCase.loadFilmsList(page)
            changeFavoriteFlagStatus(data)
            getTopFilmsUseCase.insertTopFilms(data)
            Log.d("TEST_SCROLL", "page = $page, size = ${listTopFilmsItems.value?.size}")
            isRefreshing.value = false
            page++
        }
    }

    fun changeFavoriteState(filmItem: TopFilmsEntity) {
        viewModelScope.launch {
            val newItem = filmItem.copy(favoritesFlag = !filmItem.favoritesFlag)
            getTopFilmsUseCase.updateTopFilms(newItem)
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
                (listOfFavorites?.value?.filter { it.filmId == it_film.filmId }?.size == 1)
        }
    }
}