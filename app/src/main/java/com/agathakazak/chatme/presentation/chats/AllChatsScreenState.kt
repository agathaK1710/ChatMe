package com.agathakazak.chatme.presentation.chats

import com.agathakazak.chatme.domain.entity.Chat

sealed class AllChatsScreenState {
    object Initial : AllChatsScreenState()
    object Loading : AllChatsScreenState()
    class Chats(val chats: List<Chat>) : AllChatsScreenState()
}