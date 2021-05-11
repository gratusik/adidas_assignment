package com.gratus.adidasconfirmed

import android.content.Context
import android.net.ConnectivityManager
import com.gratus.adidasconfirmed.di.component.DaggerAppComponent
import com.gratus.adidasconfirmed.di.modules.InternetModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.acra.ACRA
import org.acra.ReportField
import org.acra.annotation.AcraCore
import org.acra.annotation.AcraMailSender

@AcraMailSender(mailTo = "agratus@gmail.com")
@AcraCore(reportContent = [ReportField.STACK_TRACE, ReportField.LOGCAT])
open class BaseApplication : DaggerApplication() {
    private lateinit var connectivityManager: ConnectivityManager
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // The following line triggers the initialization of ACRA
        ACRA.init(this)
        return DaggerAppComponent.builder().application(this)
            .internetModule(InternetModule(connectivityManager))
            .build()
    }
}
