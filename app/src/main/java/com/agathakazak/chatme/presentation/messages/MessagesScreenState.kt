package com.agathakazak.chatme.presentation.messages

import com.agathakazak.chatme.domain.entity.User

sealed class MessagesScreenState {
    object Initial : MessagesScreenState()
    object Loading : MessagesScreenState()
    class Messages(val recipient: User, val sender: User) :
        MessagesScreenState()
}