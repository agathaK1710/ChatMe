package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class ReadMessagesUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(recipientId: Int) = repository.readMessages(recipientId)
}