package com.agathakazak.chatme.domain.usecases

import com.agathakazak.chatme.domain.repositoty.UserRepository
import javax.inject.Inject

class GetUserByPhoneNumberUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(phoneNumber: String) = repository.getUserByPhoneNumber(phoneNumber)
}