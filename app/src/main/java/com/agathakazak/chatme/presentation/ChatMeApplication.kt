package com.agathakazak.chatme.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.agathakazak.chatme.di.ApplicationComponent
import com.agathakazak.chatme.di.DaggerApplicationComponent

class ChatMeApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as ChatMeApplication).component
}
