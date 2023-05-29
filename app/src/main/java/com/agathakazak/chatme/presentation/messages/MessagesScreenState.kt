package com.agathakazak.chatme.presentation.messages

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.agathakazak.chatme.domain.entity.Message

sealed class MessagesScreenState {
    object Initial: MessagesScreenState()
    object Loading : MessagesScreenState()
    class Messages(val messages: SnapshotStateList<Message>) : MessagesScreenState()
}