package com.gratus.adidasconfirmed

import android.content.Context
import android.net.ConnectivityManager
import com.gratus.adidasconfirmed.di.component.DaggerAppComponent
import com.gratus.adidasconfirmed.di.modules.InternetModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class BaseApplication : DaggerApplication() {
    private lateinit var connectivityManager: ConnectivityManager
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return DaggerAppComponent.builder().application(this)
            .internetModule(InternetModule(connectivityManager))
            .build()
    }
}
