package com.agathakazak.chatme.presentation.search

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agathakazak.chatme.presentation.ViewModelFactory

@Composable
fun SearchScreen(
    viewModelFactory: ViewModelFactory
) {
    val context = LocalContext.current
    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)
    val searchScreenState = viewModel.searchScreenState.observeAsState(SearchScreenState.Initial)
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.getRegisteredContacts(getAllPhoneNumbersList(context))
            Log.d("SearchScreen","PERMISSION GRANTED")

        } else {
            Log.d("SearchScreen","PERMISSION DENIED")
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) -> {
                viewModel.getRegisteredContacts(getAllPhoneNumbersList(context))
                Log.d("SearchScreen","Code requires permission")
            }
            else -> {
                SideEffect {
                    launcher.launch(Manifest.permission.READ_CONTACTS)
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (val currentState = searchScreenState.value) {
                is SearchScreenState.Initial -> {}
                is SearchScreenState.Contacts -> {
                    if (currentState.contacts.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = "Users from your contacts:",
                            color = MaterialTheme.colors.primaryVariant,
                            fontWeight = FontWeight.Bold
                        )
                        LazyColumn {
                            items(items = currentState.contacts, key = { it.phoneNumber }) {
                                ContactItem(it)
                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = "Nobody from your contacts is registered in ChatMe.",
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            }
        }
    }
}
private fun getAllPhoneNumbersList(context: Context): List<String> {
    val phoneNumbers = mutableListOf<String>()
    val phones = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )
    if (phones != null) {
        while (phones.moveToNext()) {
            val phoneNumber = phones.getString(
                phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )
            phoneNumbers.add(phoneNumber)
        }
    }
    phones?.close()
    return phoneNumbers
}