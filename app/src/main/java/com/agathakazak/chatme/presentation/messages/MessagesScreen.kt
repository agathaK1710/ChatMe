package com.agathakazak.chatme.presentation.messages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.ChatDetail
import com.agathakazak.chatme.domain.entity.Message
import com.agathakazak.chatme.presentation.getApplicationComponent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun MessagesScreen(
    recipientId: Int,
    setChat: (ChatDetail) -> Unit,
    setOnLongClick: (Boolean) -> Unit
) {
    val component = getApplicationComponent()
        .getMessagesScreenComponentFactory()
        .create(recipientId)

    val messagesViewModel = viewModel<MessagesViewModel>(factory = component.getViewModelFactory())
    val screenState =
        messagesViewModel.messagesScreenState.observeAsState(MessagesScreenState.Initial)
    val repliedMessage = messagesViewModel.repliedMessage.observeAsState().value
    if (repliedMessage != null) {
        messagesViewModel.sendMessage(repliedMessage)
    }
//    messagesViewModel.readMessages()
    MessagesScreenContent(
        screenState,
        messagesViewModel,
        setChat = {
            setChat(it)
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
    setChat: (ChatDetail) -> Unit,
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
//            messagesViewModel.readMessages()
            Messages(
                messagesViewModel,
                messagesViewModel.messages,
                setOnLongClick = { setOnLongClick(it) },
                currentState.userId
            )
            setChat(currentState.chat)
        }

        else -> {}
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Messages(
    messagesViewModel: MessagesViewModel,
    messages: List<Message>,
    setOnLongClick: (Boolean) -> Unit,
    userId: Int
) {
    val listState = rememberLazyListState()
    var onLongClickState by rememberSaveable { mutableStateOf(false) }
    setOnLongClick(onLongClickState)
    val alpha by animateFloatAsState(
        targetValue = if (onLongClickState) 1f else 0f,
    )
    var selectedNum by rememberSaveable {
        mutableStateOf(0)
    }
    var isDialogOpened by rememberSaveable { mutableStateOf(false) }
    var lastDate by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            if (onLongClickState) {
                SelectedTopBar(
                    selectedCount = selectedNum,
                    closeClick = {
                        onLongClickState = false
                        setOnLongClick(false)
                    },
                    deleteClick = {
                        isDialogOpened = true
                    }
                )
            }
        }
    ) {
        if (isDialogOpened) {
            Dialog(selectedNum, messagesViewModel) { openState ->
                isDialogOpened = openState
            }
        }
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
                    lastDate = if (index != 0) {
                        messages[index - 1].date.convertDate(DateFormat.FULL_DATE)
                    } else {
                        ""
                    }
                    if (lastDate != message.date.convertDate(DateFormat.FULL_DATE)) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = message.date.convertDate(DateFormat.FULL_DATE),
                            color = MaterialTheme.colors.primaryVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                    var selected by rememberSaveable {
                        mutableStateOf(message.isSelected)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(
                                animationSpec = tween(
                                    durationMillis = 300
                                )
                            )
                    ) {
                        if (onLongClickState) {
                            selected = message.isSelected
                            Checkbox(
                                modifier = Modifier.scale(alpha),
                                checked = selected,
                                onCheckedChange = {
                                    messagesViewModel.changeSelectedStatus(index, it)
                                    selected = message.isSelected
                                    selectedNum = messagesViewModel.getSelectedMessages().size
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primaryVariant,
                                    uncheckedColor = MaterialTheme.colors.primaryVariant
                                )
                            )
                        }
                        if (userId == message.senderId) {
                            Spacer(modifier = Modifier.weight(1f))

                        }
                        if (messagesViewModel.getSelectedMessages().isEmpty()) {
                            onLongClickState = false
                        }
//                        if (message.isUnread && message.senderId == sender.id) {
//                            Box(
//                                modifier = Modifier
//                                    .size(8.dp)
//                                    .clip(CircleShape)
//                                    .background(MaterialTheme.colors.primaryVariant)
//                                    .align(Alignment.CenterVertically)
//                            )
//                        }
                        Card(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(3.dp)
                                .combinedClickable(
                                    onClick = {
                                        messagesViewModel.changeSelectedStatus(
                                            index,
                                            !message.isSelected
                                        )
                                        selected = message.isSelected
                                        selectedNum =
                                            messagesViewModel.getSelectedMessages().size
                                    },
                                    onLongClick = {
                                        messagesViewModel.unselectAllMessages()
                                        onLongClickState = !onLongClickState
                                        messagesViewModel.changeSelectedStatus(
                                            index,
                                            !message.isSelected
                                        )
                                        selected = message.isSelected
                                        selectedNum =
                                            messagesViewModel.getSelectedMessages().size
                                    },
                                ),
                            elevation = 2.dp,
                            backgroundColor = MaterialTheme.colors.onBackground.copy(0.6f)
                                .compositeOver(MaterialTheme.colors.background)
                        ) {
                            Row {
                                Text(
                                    text = message.messageText,
                                    modifier = Modifier
                                        .padding(
                                            start = 10.dp,
                                            top = 10.dp,
                                            end = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    color = MaterialTheme.colors.onPrimary
                                )
                                Text(
                                    modifier = Modifier.padding(top = 25.dp, end = 5.dp),
                                    text = message.date.convertDate(DateFormat.HOURS_MINUTES),
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
            }
            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    listState.animateScrollToItem(messages.lastIndex)
                }
            }
            MessageTextField(messagesViewModel)
        }
    }

}

private fun Date.convertDate(dateFormat: DateFormat): String {
    val cal = Calendar.getInstance()
    val currentYear = cal.get(Calendar.YEAR)
    cal.time = this
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minutes = cal.get(Calendar.MINUTE)
    val month = SimpleDateFormat("MMM").format(cal.time)
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val year = cal.get(Calendar.YEAR)

    return when (dateFormat) {
        DateFormat.HOURS_MINUTES -> String.format("%02d:%02d", hour, minutes)
        DateFormat.FULL_DATE -> if (year == currentYear) "$month $day" else "$month $day, $year"
    }
}

@Composable
private fun Dialog(
    selectedNum: Int,
    messagesViewModel: MessagesViewModel,
    changeOpenState: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            changeOpenState(false)
        },
        title = {
            Text(
                text = if (selectedNum == 1) stringResource(R.string.delete_message) else
                    "Delete $selectedNum messages",
                color = MaterialTheme.colors.onPrimary
            )
        },
        text = {
            Text(
                if (selectedNum == 1) stringResource(R.string.delete_question) else
                    stringResource(R.string.delete_several_question),
                color = MaterialTheme.colors.onPrimary
            )
        },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { changeOpenState(false) }
                ) {
                    Text(stringResource(R.string.cancel), color = MaterialTheme.colors.onPrimary)
                }
                TextButton(
                    onClick = {
                        messagesViewModel.deleteSelectedMessages()
                        changeOpenState(false)
                    }
                ) {
                    Text(stringResource(R.string.delete), color = Color.Red)
                }
            }
        }
    )
}

@Composable
fun MessageTextField(messagesViewModel: MessagesViewModel) {
    var messageText by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
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
//                messagesViewModel.messageNotification(recipientId, sender, messageText, context)
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