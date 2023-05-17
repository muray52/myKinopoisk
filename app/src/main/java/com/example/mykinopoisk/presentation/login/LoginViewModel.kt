package com.example.mykinopoisk.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.R
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.LoginEntity
import com.example.mykinopoisk.domain.usecases.SignInUseCase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmsRepositoryImpl(application)
    private val signInUseCase = SignInUseCase(repository)

    private val _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean>
        get() = _successLogin

    private val _errorResponseMessage = MutableLiveData<String>()
    val errorResponseMessage: LiveData<String>
        get() = _errorResponseMessage

    fun signInGuest() {
        try {
            if (signInUseCase.signInGuest()) {
                setLoginStatus(true, null)
            } else {
                setLoginStatus(
                    false,
                    getApplication<Application>().getString(R.string.toast_guest_error)
                )
            }
        } catch (exception: Exception) {
            setLoginStatus(false, exception.message)
        }
    }

    fun signIn(login: LoginEntity) {
        viewModelScope.launch {
            try {
                if (signInUseCase.signIn(login)) {
                    setLoginStatus(true, null)
                } else {
                    setLoginStatus(
                        false,
                        getApplication<Application>().getString(R.string.toast_user_error)
                    )
                }
            } catch (exception: Exception) {
                setLoginStatus(false, exception.message)
            }
        }
    }

    private fun setLoginStatus(isSuccessLogin: Boolean, errorMessage: String?) {
        _successLogin.postValue(isSuccessLogin)
        if (!isSuccessLogin) {
            _errorResponseMessage.postValue(errorMessage ?: "")
        }
    }

}