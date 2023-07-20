package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(chatId:Int) =
        repository.getChat(chatId)
}