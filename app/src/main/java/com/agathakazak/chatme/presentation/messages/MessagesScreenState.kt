package com.agathakazak.chatme.presentation.messages

import com.agathakazak.chatme.domain.entity.Message

sealed class MessagesScreenState {
    object Initial: MessagesScreenState()
    object Loading : MessagesScreenState()
    class Messages(val chats: List<Message>) : MessagesScreenState()
}