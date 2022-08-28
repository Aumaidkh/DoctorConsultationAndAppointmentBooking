package com.android.doctorce.feature_book_appointment.di

import android.app.Application
import com.android.doctorce.core.InternetConnectionChecker
import com.android.doctorce.feature_book_appointment.presentation.util.ConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesConnectivityObserver(application: Application): ConnectivityObserver {
        return InternetConnectionChecker(application.applicationContext)
    }
}