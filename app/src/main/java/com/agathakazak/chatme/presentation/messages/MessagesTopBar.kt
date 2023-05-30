package com.agathakazak.chatme.presentation.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.agathakazak.chatme.R
import com.agathakazak.chatme.domain.entity.User

@Composable
fun MessagesTopBar(user: User, backClick: () -> Unit) {
    TopAppBar(
        title = {
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
                            .size(38.dp)
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
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White
                )
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