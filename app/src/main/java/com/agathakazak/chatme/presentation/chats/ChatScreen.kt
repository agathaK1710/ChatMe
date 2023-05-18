package com.agathakazak.chatme.presentation.chats

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.presentation.ViewModelFactory

@Composable
fun ChatScreen(viewModelFactory: ViewModelFactory) {
    val chatViewModel = viewModel<ChatViewModel>(factory = viewModelFactory)
    val screenState = chatViewModel.chatScreenState.observeAsState(ChatScreenState.Initial)

    when (val currentState = screenState.value) {
        is ChatScreenState.AllChats -> {
            LazyColumn{
                items(items = currentState.chats, key = { it.companion.id }) {
                     ChatItem(it.companion, it.lastMessage, it.isUnread)
                }
            }
        }
        else -> {}
    }

}