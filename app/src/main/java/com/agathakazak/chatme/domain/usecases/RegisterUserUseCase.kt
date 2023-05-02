package com.agathakazak.chatme.domain.usecases

import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.domain.repositoty.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(user: User) = repository.registerUser(user)
}