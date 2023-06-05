package com.agathakazak.chatme.presentation.messages

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.agathakazak.chatme.R

@Composable
fun SelectedTopBar(selectedCount: Int, closeClick: () -> Unit, deleteClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = selectedCount.toString(), color = Color.White)
        },
        navigationIcon = {
            IconButton(onClick = { closeClick() }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(25.dp),
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                deleteClick() 
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.bin),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(20.dp),
                    tint = Color.White
                )
            }
        }
    )
}