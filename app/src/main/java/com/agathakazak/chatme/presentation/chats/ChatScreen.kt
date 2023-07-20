package com.agathakazak.chatme.presentation.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.presentation.ViewModelFactory

@Composable
fun ChatScreen(
    viewModelFactory: ViewModelFactory,
    navigateToMessages: (id: Int) -> Unit
) {
    val chatViewModel = viewModel<ChatsViewModel>(factory = viewModelFactory)
    val screenState = chatViewModel.chatScreenState.observeAsState(AllChatsScreenState.Initial)

    when (val currentState = screenState.value) {
        is AllChatsScreenState.Chats -> {
            LazyColumn {
                items(items = currentState.chats, key = {it.id }) {
                    ChatItem(it, navigateToMessages)
                }
            }
        }

        is AllChatsScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
            }
        }

        else -> {}
    }
}