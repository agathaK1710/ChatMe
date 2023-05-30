package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class DeleteMessageUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteMessage(id)
}