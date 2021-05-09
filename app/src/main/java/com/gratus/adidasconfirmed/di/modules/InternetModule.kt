package com.gratus.adidasconfirmed.di.modules

import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Check internet connection module
@Module(includes = [ViewModelModule::class])
class InternetModule(private val connectivityManager: ConnectivityManager) {
    @Provides
    @Singleton
    fun provideNetworkHelper(): ConnectivityManager {
        return connectivityManager
    }
}
