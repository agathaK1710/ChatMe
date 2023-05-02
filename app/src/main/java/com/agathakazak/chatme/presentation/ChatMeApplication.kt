package com.agathakazak.chatme.presentation

import android.app.Application
import com.agathakazak.chatme.di.ApplicationComponent
import com.agathakazak.chatme.di.DaggerApplicationComponent

class ChatMeApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}