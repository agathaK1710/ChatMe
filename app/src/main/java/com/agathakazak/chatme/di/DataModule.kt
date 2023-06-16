package com.agathakazak.chatme.di

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
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

        @ApplicationScope
        @Provides
        fun provideNotificationManager(
            context: Context
        ): NotificationManager {
            return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        @ApplicationScope
        @Provides
        fun provideNotificationBuilder(
            context: Context
        ): NotificationCompat.Builder {
            return NotificationCompat.Builder(context)
        }
    }
}