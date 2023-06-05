package com.agathakazak.chatme.domain.usecase

import com.agathakazak.chatme.domain.repository.UserRepository
import javax.inject.Inject

class DeleteMessagesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(ids: List<Int>) = repository.deleteMessages(ids)
}