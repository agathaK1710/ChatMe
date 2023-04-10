package com.agathakazak.chatme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.agathakazak.chatme.presentation.main.RegistrationScreen
import com.agathakazak.chatme.ui.theme.ChatMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatMeTheme {
               RegistrationScreen(this)
            }
        }
    }
}
