package com.agathakazak.chatme.presentation.chats

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agathakazak.chatme.R

@Composable
fun ChatTopBar(
    menuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White
            )
        },
        navigationIcon = {
                IconButton(onClick = {
                    menuClick()
                }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(30.dp),
                        tint = Color.White
                    )
                }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}