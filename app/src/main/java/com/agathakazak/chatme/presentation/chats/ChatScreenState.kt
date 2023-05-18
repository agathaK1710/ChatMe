package com.agathakazak.chatme.presentation.chats

import com.agathakazak.chatme.domain.entity.Chat

sealed class ChatScreenState(chats: List<Chat>? = null, userId: Int? = null) {
    object Initial : ChatScreenState()
    class AllChats(val chats: List<Chat>) : ChatScreenState(chats)
    class UsersChat(userId: Int) : ChatScreenState(userId = userId)
}