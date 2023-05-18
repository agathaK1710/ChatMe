package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.entity.UserLogin
import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userLogin: UserLogin) = repository.loginUser(userLogin)
}