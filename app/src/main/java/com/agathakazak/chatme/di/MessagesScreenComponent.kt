package com.agathakazak.chatme.di

import com.agathakazak.chatme.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [MessagesViewModelModule::class]
)
interface MessagesScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance chatId: Int
        ): MessagesScreenComponent
    }
}