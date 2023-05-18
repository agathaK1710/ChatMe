package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(messageRequest: MessageRequest) =
        repository.sendMessage(messageRequest)
}