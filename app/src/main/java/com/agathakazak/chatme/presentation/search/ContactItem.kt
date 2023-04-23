package com.agathakazak.chatme.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ContactItem(contact: Contact) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = contact.name,
                modifier = Modifier.padding(start = 8.dp),
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = contact.number,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}