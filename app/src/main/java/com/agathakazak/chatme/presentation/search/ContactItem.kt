package com.agathakazak.chatme.presentation.search

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.User


@Composable
fun ContactItem(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(8.dp),
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
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}