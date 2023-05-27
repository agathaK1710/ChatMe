package com.agathakazak.chatme.di

import androidx.lifecycle.ViewModel
import com.agathakazak.chatme.presentation.chats.ChatsViewModel
import com.agathakazak.chatme.presentation.login.LoginViewModel
import com.agathakazak.chatme.presentation.registration.RegistrationViewModel
import com.agathakazak.chatme.presentation.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    @Binds
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ChatsViewModel::class)
    @Binds
    fun bindChatViewModel(viewModel: ChatsViewModel): ViewModel
}