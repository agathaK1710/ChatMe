package com.agathakazak.chatme.presentation.chats

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.agathakazak.chatme.domain.entity.User

@Composable
fun ChatItem(user: User, message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                model = "${user.imageUrl}",
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Column(modifier = Modifier.fillMaxWidth()) {
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
        }
    }
}