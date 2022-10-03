package com.application.portdex.di

import android.app.Application
import com.application.portdex.data.local.AppDatabase
import com.application.portdex.data.local.chat.Message
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

//    @Provides
//    @Singleton
//    fun provideAppDatabase(application: Application): AppDatabase {
//        return AppDatabase.getDatabase(application)
//    }
//
//    @Provides
//    @Singleton
//    fun provideMessageDao(appDatabase: AppDatabase): Message {
//        return appDatabase.message
//    }
}