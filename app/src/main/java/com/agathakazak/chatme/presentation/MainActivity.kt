package com.agathakazak.chatme.presentation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.agathakazak.chatme.presentation.main.MainScreen
import com.agathakazak.chatme.ui.theme.ChatMeTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ChatMeApplication).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            ChatMeTheme {
               MainScreen(viewModelFactory)
            }
        }
    }
}
