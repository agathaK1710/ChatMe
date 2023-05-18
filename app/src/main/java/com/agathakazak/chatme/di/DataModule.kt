package com.agathakazak.chatme.di

import android.content.Context
import android.content.SharedPreferences
import com.agathakazak.chatme.data.network.ApiFactory
import com.agathakazak.chatme.data.network.ApiService
import com.agathakazak.chatme.data.repository.UserRepositoryImpl
import com.agathakazak.chatme.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideSharedPreferences(
            context: Context
        ): SharedPreferences {
            return context.getSharedPreferences("chatMe", Context.MODE_PRIVATE)
        }
    }
}