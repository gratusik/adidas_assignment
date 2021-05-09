package com.gratus.adidasconfirmed.di.modules

import android.app.Application
import android.content.Context
import com.gratus.adidasconfirmed.data.service.local.pref.AppPrefHelper
import com.gratus.adidasconfirmed.data.service.local.pref.AppPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): AppPrefHelper {
        return appPreferencesHelper
    }
}
