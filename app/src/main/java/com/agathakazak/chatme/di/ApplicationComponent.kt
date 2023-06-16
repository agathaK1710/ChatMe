package com.agathakazak.chatme.di

import android.content.Context
import com.agathakazak.chatme.presentation.messages.NotificationReceiver
import com.agathakazak.chatme.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelsModule::class])
interface ApplicationComponent {
    fun inject(receiver: NotificationReceiver)
    fun getViewModelFactory(): ViewModelFactory
    fun getMessagesScreenComponentFactory(): MessagesScreenComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

}