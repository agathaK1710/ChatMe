package com.agathakazak.chatme.presentation.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.MessageRequest
import com.agathakazak.chatme.presentation.ViewModelFactory

@Composable
fun MessagesScreen(viewModelFactory: ViewModelFactory) {
    val chatViewModel = viewModel<ChatViewModel>(factory = viewModelFactory)
    val messages = remember { mutableStateListOf<Message>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = messages, key = { it.id!!}) {
                Card(modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 5.dp, bottom = 5.dp), elevation = 10.dp) {
                    Text(text = it.messageText)
                }
            }
        }
        MessageTextField(chatViewModel)
    }
}

@Composable
fun MessageTextField(chatViewModel: ChatViewModel) {
    var messageText by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.background)
            .drawBehind {
                drawLine(
                    Color(0xFF9B9999),
                    Offset(0f, -density / 2),
                    Offset(size.width, -density / 2),
                    density / 2
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {

            },
            modifier = Modifier
                .size(20.dp)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.emoji),
                contentDescription = null,
                tint = MaterialTheme.colors.secondary
            )
        }
        TextField(
            modifier = Modifier.weight(5f),
            value = messageText,
            onValueChange = {
                messageText = it
            },
            placeholder = {
                Text(text = "Message", color = MaterialTheme.colors.secondary)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                cursorColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.background,
                unfocusedIndicatorColor = MaterialTheme.colors.background,
                disabledIndicatorColor = MaterialTheme.colors.background,
                textColor = MaterialTheme.colors.onPrimary
            )
        )
        if (messageText.isBlank()) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.attachment),
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .size(25.dp)
                        .weight(1f)
                )
            }
        } else {
            IconButton(onClick = {
//                chatViewModel.sendMessage(
//                    MessageRequest(
//
//                    )
//                )
                messageText = ""
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .size(30.dp)
                        .weight(1f)
                )
            }
        }
    }
}