package com.example.mykinopoisk.presentation.detailedinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.usecases.GetDetailedFilmInfoUseCase
import kotlinx.coroutines.launch

class DetailedInfoViewModel : ViewModel() {

    private val repository = FilmsRepositoryImpl()
    private val getDetailedFilmInfoUseCase = GetDetailedFilmInfoUseCase(repository)

    private val _detailedInfo = MutableLiveData<DetailedFilmEntity>()
    val detailedInfo: LiveData<DetailedFilmEntity>
        get() = _detailedInfo

    fun getDetailedInfo(filmId: Int) {
        viewModelScope.launch {
            _detailedInfo.postValue(getDetailedFilmInfoUseCase.getFilmDetailedDescription(filmId))
        }
    }

}