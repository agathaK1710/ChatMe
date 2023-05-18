package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class GetChatsForUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(senderId: Int) = repository.getChatsForUser(senderId)
}