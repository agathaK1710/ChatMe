package com.agathakazak.chatme.presentation.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.User

@Composable
fun ChatItem(
    user: User,
    message: String,
    isUnread: Boolean,
    navigateToMessages: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToMessages(user.id)
            },
        backgroundColor = MaterialTheme.colors.background,
        shape = RectangleShape,
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (user.imageUrl != null) {
                AsyncImage(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape),
                    model = "${user.imageUrl}",
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.random_background),
                        colorFilter = ColorFilter.tint(
                            color = Color(user.stubImageColor)
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = user.firstName.first().uppercase(),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Column(modifier = Modifier.weight(9f)) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = message,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            }

            if (isUnread) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primaryVariant)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}