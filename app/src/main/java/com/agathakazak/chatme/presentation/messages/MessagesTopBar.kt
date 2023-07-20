package com.agathakazak.chatme.presentation.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.Chat
import com.agathakazak.chatme.domain.entity.ChatDetail

@Composable
fun MessagesTopBar(chat: ChatDetail, backClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chat.chatImageUrl != null) {
                    AsyncImage(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape),
                        model = "${chat.chatImageUrl}",
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.random_background),
                            colorFilter = ColorFilter.tint(
                                color = Color(chat.stubImageColor)
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = chat.chatName.first().uppercase(),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = chat.chatName,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.White
                    )
                    Text(
                        text = "${chat.members.size} members",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.White
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                backClick()
            }
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(25.dp),
                    tint = Color.White
                )
            }
        }
    )
}