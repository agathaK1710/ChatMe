package com.agathakazak.chatme.presentation.search

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val activity = context as Activity
    val viewModel: SearchViewModel = viewModel()
    val searchScreenState = viewModel.searchScreenState.observeAsState(SearchScreenState.Initial)
    Box(modifier = Modifier.fillMaxSize()) {
        if (!hasPermission(context)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                1
            )
        } else {
            viewModel.getRegisteredContacts(getAllContactsList(context))
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (val currentState = searchScreenState.value) {
                is SearchScreenState.Initial -> {}
                is SearchScreenState.Contacts -> {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Users from your contacts:",
                        color = MaterialTheme.colors.primaryVariant,
                        fontWeight = FontWeight.Bold
                    )
                    LazyColumn {
                        items(items = currentState.contacts, key = { it.number }) {
                            ContactItem(contact = it)
                        }
                    }
                }
            }
        }
    }
}

fun hasPermission(context: Context) =
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED

private fun getAllContactsList(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val phones = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )
    if (phones != null) {
        while (phones.moveToNext()) {
            val name = phones.getString(
                phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            )
            val phoneNumber = phones.getString(
                phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )
            val contact = Contact(name, phoneNumber)
            contacts.add(contact)
        }
    }
    phones?.close()
    return contacts
}