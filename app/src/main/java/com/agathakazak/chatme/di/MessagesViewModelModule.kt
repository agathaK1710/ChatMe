package com.agathakazak.chatme.di

import androidx.lifecycle.ViewModel
import com.agathakazak.chatme.presentation.messages.MessagesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MessagesViewModelModule {
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    @Binds
    fun bindMessagesViewModel(viewModel: MessagesViewModel): ViewModel
}