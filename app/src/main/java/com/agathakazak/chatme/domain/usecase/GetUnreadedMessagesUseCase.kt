package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class GetUnreadedMessagesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(senderId: Int, recipientId: Int) {
        repository.getUnreadedMessages(senderId, recipientId)
    }
}