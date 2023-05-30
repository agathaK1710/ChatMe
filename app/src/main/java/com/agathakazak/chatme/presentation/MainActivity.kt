package com.agathakazak.chatme.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.EmojiSupportMatch
import com.agathakazak.chatme.presentation.main.MainScreen
import com.agathakazak.chatme.ui.theme.ChatMeTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatMeTheme {
                val component = getApplicationComponent()
                MainScreen(component.getViewModelFactory())
            }
        }
    }
}
