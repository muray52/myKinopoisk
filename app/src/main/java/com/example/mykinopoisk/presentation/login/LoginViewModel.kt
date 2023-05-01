package com.example.mykinopoisk.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.example.mykinopoisk.domain.model.LoginEntity
import com.example.mykinopoisk.domain.usecases.SignInUseCase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmsRepositoryImpl(application)
    private val signInUseCase = SignInUseCase(repository)

    val successLogin = MutableLiveData<Boolean>()

    fun signInGuest() {
        if (signInUseCase.signInGuest()) {
            successLogin.value = true
        }
    }

    fun signIn(login: LoginEntity) {
        viewModelScope.launch {
            if (signInUseCase.signIn(login)) {
                successLogin.value = true
            }
        }
    }

}