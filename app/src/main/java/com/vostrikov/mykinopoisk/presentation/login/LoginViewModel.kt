package com.vostrikov.mykinopoisk.presentation.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vostrikov.mykinopoisk.R
import com.vostrikov.mykinopoisk.domain.model.LoginEntity
import com.vostrikov.mykinopoisk.domain.usecases.SignInUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val application: Application,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

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
                    application.getString(R.string.toast_guest_error)
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
                        application.getString(R.string.toast_user_error)
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