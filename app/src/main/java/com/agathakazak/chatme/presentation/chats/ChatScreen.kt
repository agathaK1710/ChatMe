package com.agathakazak.chatme.presentation.chats

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.agathakazak.chatme.domain.entity.Chat

@Composable
fun ChatScreen(chats: List<Chat>) {
    LazyColumn {
        items(items = chats, key = { it.user.email }) {
            ChatItem(user = it.user, message = it.message)
        }
    }
}