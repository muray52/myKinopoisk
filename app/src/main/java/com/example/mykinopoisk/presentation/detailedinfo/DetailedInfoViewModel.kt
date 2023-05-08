package com.example.mykinopoisk.presentation.detailedinfo

import android.app.Application
import androidx.lifecycle.*
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.DetailedFilmEntity
import com.example.mykinopoisk.domain.usecases.GetDetailedFilmInfoUseCase
import kotlinx.coroutines.launch

class DetailedInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmsRepositoryImpl(application)
    private val getDetailedFilmInfoUseCase = GetDetailedFilmInfoUseCase(repository)

    private val _detailedInfo = MutableLiveData<DetailedFilmEntity>()
    val detailedInfo: LiveData<DetailedFilmEntity>
        get() = _detailedInfo

    private val _errorResponseMessage = MutableLiveData<String>()
    val errorResponseMessage: LiveData<String>
        get() = _errorResponseMessage

    fun getDetailedInfo(filmId: Int) {
        viewModelScope.launch {
            try {
                _detailedInfo.postValue(getDetailedFilmInfoUseCase.getFilmDetailedDescription(filmId))
            } catch (exception: Exception) {
                _errorResponseMessage.postValue(exception.message)
            }
        }
    }

}