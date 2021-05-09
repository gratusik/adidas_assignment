package com.gratus.adidasconfirmed.di.component

import android.app.Application
import com.gratus.adidasconfirmed.BaseApplication
import com.gratus.adidasconfirmed.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class, NetworkModule::class,
        InternetModule::class, ContextModule::class,
        ActivityBindingModule::class, RoomModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        fun internetModule(internetModule: InternetModule): Builder
    }
}
