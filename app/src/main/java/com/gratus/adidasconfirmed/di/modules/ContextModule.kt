package com.gratus.adidasconfirmed.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}
