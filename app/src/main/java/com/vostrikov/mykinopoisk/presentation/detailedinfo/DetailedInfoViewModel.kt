package com.vostrikov.mykinopoisk.presentation.detailedinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vostrikov.mykinopoisk.domain.model.DetailedFilmEntity
import com.vostrikov.mykinopoisk.domain.usecases.GetDetailedFilmInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailedInfoViewModel @Inject constructor(
    private val getDetailedFilmInfoUseCase: GetDetailedFilmInfoUseCase
) : ViewModel() {

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