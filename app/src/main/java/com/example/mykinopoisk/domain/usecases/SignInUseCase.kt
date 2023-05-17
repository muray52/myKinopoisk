package com.example.mykinopoisk.domain.usecases

import com.example.mykinopoisk.domain.FilmsRepository
import com.example.mykinopoisk.domain.model.LoginEntity

class SignInUseCase(private val repository: FilmsRepository) {

    fun signInGuest(): Boolean {
        return repository.signInGuest()
    }

    suspend fun signIn(login: LoginEntity): Boolean {
        return repository.signIn(login)
    }
}