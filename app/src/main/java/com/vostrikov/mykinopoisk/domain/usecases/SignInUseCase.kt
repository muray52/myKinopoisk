package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.LoginEntity

class SignInUseCase(private val repository: FilmsRepository) {

    fun signInGuest(): Boolean {
        return repository.signInGuest()
    }

    suspend fun signIn(login: LoginEntity): Boolean {
        return repository.signIn(login)
    }
}