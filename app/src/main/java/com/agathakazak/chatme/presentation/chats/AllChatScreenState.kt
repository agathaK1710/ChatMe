package com.agathakazak.chatme.presentation.chats

import com.agathakazak.chatme.domain.entity.Chat

sealed class AllChatScreenState {
    object Initial : AllChatScreenState()
    object Loading : AllChatScreenState()
    class Chats(val chats: List<Chat>) : AllChatScreenState()
}