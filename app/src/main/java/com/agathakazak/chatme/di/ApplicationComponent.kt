package com.agathakazak.chatme.di

import android.content.Context
import com.agathakazak.chatme.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelsModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

}