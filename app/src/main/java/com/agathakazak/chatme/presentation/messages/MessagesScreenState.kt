package com.agathakazak.chatme.presentation.messages

import com.agathakazak.chatme.domain.entity.ChatDetail

sealed class MessagesScreenState {
    object Initial : MessagesScreenState()
    object Loading : MessagesScreenState()
    class Messages(val chat: ChatDetail, val userId: Int) :
        MessagesScreenState()
}