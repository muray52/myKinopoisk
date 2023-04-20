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

    val listTopFilmsItems = liveData {
        isRefreshing.value = true
        val data = getTopFilmsUseCase.loadFilmsList(page)
        emit(data)
        Log.d("TEST_SCROLL", "page = $page")
        page++
        isRefreshing.value = false
    }

    val listOfFavorites = getTopFilmsUseCase.loadFilmFavorites()

//    init {
//        viewModelScope.launch {
//            _listTopFilmsItems = getTopFilmsUseCase.loadFilmsList(page)
//        }
//    }

    fun loadFilms(refresh: Boolean = false) {
        if (refresh) {
            page = 1
        }
        viewModelScope.launch {
            if (page == 1) {
                isRefreshing.value = true
                listTopFilmsItems.value?.clear()
            }
            val data = getTopFilmsUseCase.loadFilmsList(page)
            listTopFilmsItems.value?.addAll(data)
            Log.d("TEST_SCROLL", "page = $page, size = ${listTopFilmsItems.value?.size}")
            isRefreshing.value = false
        }
        page++
    }

    fun changeFavoriteState(filmItem: TopFilmsEntity) {
        viewModelScope.launch {
            if (filmItem.favoritesFlag) {
                addOrRemoveFavoriteFilmsUseCase.removeFilmFromFavorite(filmItem.filmId)
            } else {
                addOrRemoveFavoriteFilmsUseCase.addFilmToFavorite(filmItem)
            }
        }

        println("listOfFavorites = ${listOfFavorites.value?.size}")
    }

}