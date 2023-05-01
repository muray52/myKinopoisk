package com.example.mykinopoisk.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    fun signInGuest() {
        if (signInUseCase.signInGuest()) {
            _successLogin.value = true
        }
    }

    fun signIn(login: LoginEntity) {
        viewModelScope.launch {
            if (signInUseCase.signIn(login)) {
                _successLogin.value = true
            }
        }
    }

}