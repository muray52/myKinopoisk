package com.vostrikov.mykinopoisk.domain.usecases

import com.vostrikov.mykinopoisk.domain.FilmsRepository
import com.vostrikov.mykinopoisk.domain.model.LoginEntity
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: FilmsRepository
) {

    fun signInGuest(): Boolean {
        return repository.signInGuest()
    }

    suspend fun signIn(login: LoginEntity): Boolean {
        return repository.signIn(login)
    }
}