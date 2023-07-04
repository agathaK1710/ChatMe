package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.entity.UserRegister
import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(user: UserRegister) = repository.registerUser(user)
}