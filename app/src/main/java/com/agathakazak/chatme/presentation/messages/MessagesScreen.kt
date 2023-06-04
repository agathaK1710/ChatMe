package com.agathakazak.chatme.presentation.messages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.domain.entity.User
import com.agathakazak.chatme.presentation.getApplicationComponent
import kotlinx.coroutines.launch

@Composable
fun MessagesScreen(
    recipientId: Int,
    setUser: (User) -> Unit,
    setOnLongClick: (Boolean) -> Unit
) {
    val component = getApplicationComponent()
        .getMessagesScreenComponentFactory()
        .create(recipientId)

    val messagesViewModel = viewModel<MessagesViewModel>(factory = component.getViewModelFactory())
    val screenState =
        messagesViewModel.messagesScreenState.observeAsState(MessagesScreenState.Initial)

    MessagesScreenContent(
        screenState,
        messagesViewModel,
        setUser = {
            setUser(it)
        },
        setOnLongClick = {
            setOnLongClick(it)
        }
    )
}

@Composable
private fun MessagesScreenContent(
    screenState: State<MessagesScreenState>,
    messagesViewModel: MessagesViewModel,
    setUser: (User) -> Unit,
    setOnLongClick: (Boolean) -> Unit
) {
    when (val currentState = screenState.value) {
        is MessagesScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
            }
        }

        is MessagesScreenState.Messages -> {
            Messages(
                messagesViewModel,
                messagesViewModel.messages,
                setOnLongClick = { setOnLongClick(it) }
            )
            setUser(currentState.recipient)
        }

        else -> {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Messages(
    messagesViewModel: MessagesViewModel,
    messages: List<Message>,
    setOnLongClick: (Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var onLongClickState by rememberSaveable { mutableStateOf(false) }
    setOnLongClick(onLongClickState)
    val alpha by animateFloatAsState(
        targetValue = if (onLongClickState) 1f else 0f,
    )
    var selectedNum by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            if (onLongClickState) {
                SelectedTopBar(
                    selectedCount = selectedNum,
                    closeClick = {
                        onLongClickState = false
                        setOnLongClick(false)
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colors.background),
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                state = listState
            ) {
                itemsIndexed(items = messages) { index, message ->
                    var selected by rememberSaveable {
                        mutableStateOf(message.isSelected)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (onLongClickState) {
                            selected = message.isSelected
                            Checkbox(
                                modifier = Modifier.scale(alpha),
                                checked = selected,
                                onCheckedChange = {
                                    messagesViewModel.selectMessage(index, it)
                                    selected = message.isSelected
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primaryVariant,
                                    uncheckedColor = MaterialTheme.colors.primaryVariant
                                )
                            )
                        }
                        if (message.recipientId == messagesViewModel.recipientId) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        if (messagesViewModel.getSelectedNum() == 0) {
                            onLongClickState = false
                        }

                        Card(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(2.dp)
                                .combinedClickable(
                                    onClick = {
                                        messagesViewModel.selectMessage(
                                            index,
                                            !message.isSelected
                                        )
                                        selected = message.isSelected
                                        selectedNum = messagesViewModel.getSelectedNum()
                                    },
                                    onLongClick = {
                                        messagesViewModel.unselectAllMessages()
                                        onLongClickState = !onLongClickState
                                        messagesViewModel.selectMessage(
                                            index,
                                            !message.isSelected
                                        )
                                        selected = message.isSelected
                                        selectedNum = messagesViewModel.getSelectedNum()
                                    },
                                ),
                            elevation = 5.dp,
                            backgroundColor = MaterialTheme.colors.onBackground.copy(0.6f)
                                .compositeOver(MaterialTheme.colors.background)
                        ) {
                            Text(
                                text = message.messageText,
                                modifier = Modifier
                                    .padding(10.dp),
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            }
            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(messages.lastIndex)
                    }
                }
            }
            MessageTextField(messagesViewModel)
        }
    }

}

@Composable
fun MessageTextField(messagesViewModel: MessagesViewModel) {
    var messageText by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.background)
            .drawBehind {
                drawLine(
                    Color(0xFF9B9999),
                    Offset(0f, -density / 3),
                    Offset(size.width, -density / 3),
                    density / 3
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
                messagesViewModel.sendMessage(messageText)
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